package jp.yitt.navigation_panel.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class ColoredFragment extends Fragment {

    private static final String KEY_COLOR_RES_ID = "color_res_id";

    public static ColoredFragment newInstance(int colorResId) {
        ColoredFragment fragment = new ColoredFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_COLOR_RES_ID, colorResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_colored, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int colorResId = getArguments().getInt(KEY_COLOR_RES_ID);

        FrameLayout rootLayout = (FrameLayout) view.findViewById(R.id.layout_root);
        rootLayout.setBackgroundColor(ResourcesCompat.getColor(
                getResources(), colorResId, getActivity().getTheme()));
    }
}
