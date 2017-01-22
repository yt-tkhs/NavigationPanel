package jp.yitt.navigation_panel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.MenuRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class NavigationPanel extends FrameLayout {

    private static final String TAG = "TopNavigationPanel";

    public static final int STATE_EXPANDED = 1;
    public static final int STATE_HIDDEN = 2;

    @IntDef({STATE_EXPANDED, STATE_HIDDEN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public static final int MODE_DEFAULT = 1;
    public static final int MODE_FOCUS = 2;

    @IntDef({MODE_DEFAULT, MODE_FOCUS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HideMode {
    }

    private static final int ROW_ITEM_NUM = 3;
    private static final int GRID_SPAN_SIZE = 6;
    private static final int ROW_SPACING_DP = 24;

    // Widgets
    private LinearLayout panelLayout;
    private ImageButton navigationButton;
    private RecyclerView itemRecycler;

    private GridLayoutManager gridLayoutManager;
    private PanelItemAdapter panelItemAdapter;

    private MenuInflater menuInflater;
    private Menu menu;

    private int navigationButtonCenter = -1;
    private int maxRadius = -1;

    @State
    private int state;

    private OnItemSelectedListener listener;

    public NavigationPanel(Context context) {
        this(context, null);
    }

    public NavigationPanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout(context);

        // initialize states
        if (!isInEditMode()) {
            state = STATE_HIDDEN;
            setVisibility(View.INVISIBLE);
            itemRecycler.setAlpha(0f);
        }

        // Create the menu
        menu = new MenuBuilder(getContext());

        // Custom Attributes
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.NavigationPanel, defStyleAttr, 0);

        // panel_background
        ViewCompat.setBackground(panelLayout, a.getDrawable(R.styleable.NavigationPanel_panel_background));

        // panel_menu
        if (a.hasValue(R.styleable.NavigationPanel_panel_menu)) {
            int menuResId = a.getResourceId(R.styleable.NavigationPanel_panel_menu, 0);
            inflateMenu(menuResId);
        }

        a.recycle();
    }

    private void inflateLayout(Context context) {
        View view = View.inflate(context, R.layout.top_navigation_panel, this);

        panelLayout = (LinearLayout) view.findViewById(R.id.panelLayout);
        navigationButton = (ImageButton) view.findViewById(R.id.navigation_button);
        itemRecycler = (RecyclerView) view.findViewById(R.id.item_recycler);

        navigationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide(MODE_DEFAULT);
            }
        });
    }

    public void inflateMenu(@MenuRes int menuResId) {
        getMenuInflater().inflate(menuResId, menu);

        if (gridLayoutManager == null) {
            gridLayoutManager = new GridLayoutManager(getContext(), GRID_SPAN_SIZE);
        }

        int row = (menu.size() + (ROW_ITEM_NUM - 1)) / ROW_ITEM_NUM;
        final int lastRowItemNum = menu.size() - ((row - 1) * ROW_ITEM_NUM);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position < menu.size() - lastRowItemNum) {
                    return (GRID_SPAN_SIZE / ROW_ITEM_NUM);
                }
                return (GRID_SPAN_SIZE / lastRowItemNum);
            }
        });

        if (panelItemAdapter == null) {
            panelItemAdapter = new PanelItemAdapter(menu);
            panelItemAdapter.setOnItemSelectedListener(listener);
        }

        int spacingPx = Utils.dpToPx(getContext(), ROW_SPACING_DP);
        itemRecycler.setItemAnimator(null);
        itemRecycler.addItemDecoration(new RowSpacingDecoration(spacingPx, ROW_ITEM_NUM));
        itemRecycler.setLayoutManager(gridLayoutManager);
        itemRecycler.setAdapter(panelItemAdapter);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
        if (panelItemAdapter != null) {
            panelItemAdapter.setOnItemSelectedListener(listener);
        }
    }

    public void setSelectedItemIndex(int index) {
        panelItemAdapter.setSelectedItemIndex(index);
    }

    @State
    public int getState() {
        return this.state;
    }

    public void expand() {
        initAnimationParams();
        Animator panelAnimator = ViewAnimationUtils.createCircularReveal(this,
                navigationButtonCenter, navigationButtonCenter, navigationButtonCenter, maxRadius);
        final ViewPropertyAnimator itemsAnimator = itemRecycler.animate()
                .alphaBy(0f)
                .alpha(1f)
                .setDuration(400)
                .setInterpolator(new AccelerateDecelerateInterpolator());

        setVisibility(View.VISIBLE);
        panelAnimator.start();
        itemsAnimator.start();
    }

    public void hide(@HideMode int hideMode) {
        if (hideMode == MODE_DEFAULT) {
            hide();
        } else if (hideMode == MODE_FOCUS) {
            hideWithFocusSelected();
        } else {
            throw new IllegalArgumentException("Illegal hide mode argument: " + hideMode);
        }
    }

    private void hide() {
        initAnimationParams();

        Animator panelAnimator = ViewAnimationUtils.createCircularReveal(this,
                navigationButtonCenter, navigationButtonCenter, maxRadius, 0f);
        ViewPropertyAnimator itemsAnimator = itemRecycler.animate()
                .alphaBy(1f)
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(new AccelerateDecelerateInterpolator());

        panelAnimator.setDuration(400);
        panelAnimator.setInterpolator(new DecelerateInterpolator());
        panelAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(View.INVISIBLE);
            }
        });

        itemsAnimator.start();
        panelAnimator.start();
    }

    private void hideWithFocusSelected() {
        initAnimationParams();

        int selectedItemIndex = panelItemAdapter.getSelectedItemIndex();
        View view = itemRecycler.findViewHolderForLayoutPosition(selectedItemIndex).itemView;
        if (view == null) {
            Log.d(TAG, "item view is null");
            hide();
            return;
        }

        int centerX = (int) (itemRecycler.getX() + view.getX()) + view.getWidth() / 2;
        int centerY = (int) (itemRecycler.getY() + view.getY()) + view.getHeight() / 2;

        Animator panelAnimator = ViewAnimationUtils.createCircularReveal(this,
                centerX, centerY, maxRadius, 0f);

        panelAnimator.setDuration(400);
        panelAnimator.setInterpolator(new DecelerateInterpolator());
        panelAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(View.INVISIBLE);
            }
        });

        panelAnimator.start();
    }

    private void initAnimationParams() {
        if (navigationButtonCenter <= 0) {
            navigationButtonCenter = navigationButton.getMeasuredWidth() / 2;
        }
        if (maxRadius <= 0) {
            double a = Math.pow(getWidth() - navigationButtonCenter, 2);
            double b = Math.pow(getHeight() - navigationButtonCenter, 2);
            maxRadius = (int) Math.sqrt(a + b);
        }
    }

    private MenuInflater getMenuInflater() {
        if (menuInflater == null) {
            menuInflater = new SupportMenuInflater(getContext());
        }
        return menuInflater;
    }
}