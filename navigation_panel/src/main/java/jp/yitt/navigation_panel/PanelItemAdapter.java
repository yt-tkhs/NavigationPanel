package jp.yitt.navigation_panel;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class PanelItemAdapter extends RecyclerView.Adapter<PanelItemAdapter.ViewHolder> {

    private static final float SELECTED_ITEM_ALPHA = 1f;
    private static final float NOT_SELECTED_ITEM_ALPHA = 0.55f;

    private Menu menu;
    private OnItemSelectedListener listener;
    private int selectedItemIndex;

    @ColorInt
    private int itemTextColor;

    public PanelItemAdapter(Menu menu) {
        this(menu, -1);
    }

    public PanelItemAdapter(Menu menu, int selectedItemIndex) {
        this.menu = menu;
        this.selectedItemIndex = selectedItemIndex;
        this.itemTextColor = Color.WHITE;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_panel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(menu.getItem(position), position);
    }

    @Override
    public int getItemCount() {
        if (menu != null) {
            return menu.size();
        } else {
            return 0;
        }
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        int oldIndex = this.selectedItemIndex;
        this.selectedItemIndex = selectedItemIndex;
        notifyItemChanged(oldIndex);
        notifyItemChanged(this.selectedItemIndex);
    }

    public int getSelectedItemIndex() {
        return this.selectedItemIndex;
    }

    public void setItemTextColor(@ColorInt int color) {
        this.itemTextColor = color;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout itemLayout;
        private ImageView iconImage;
        private TextView titleText;

        ViewHolder(View itemView) {
            super(itemView);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.layout_item);
            iconImage = (ImageView) itemView.findViewById(R.id.image_icon);
            titleText = (TextView) itemView.findViewById(R.id.text_title);
        }

        void bind(final MenuItem item, final int position) {
            itemLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setSelectedItemIndex(position);
                    if (listener == null) {
                        return;
                    }
                    listener.onItemSelected(item.getItemId());
                }
            });

            iconImage.setImageDrawable(item.getIcon());
            titleText.setText(item.getTitle());
            titleText.setTextColor(itemTextColor);

            if (position == selectedItemIndex) {
                itemView.setAlpha(SELECTED_ITEM_ALPHA);
            } else {
                itemView.animate().alpha(NOT_SELECTED_ITEM_ALPHA).setDuration(250).start();
            }
        }
    }
}