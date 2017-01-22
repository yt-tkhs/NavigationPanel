package jp.yitt.navigation_panel;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RowSpacingDecoration extends RecyclerView.ItemDecoration {

    private int spacingPx;
    private int rowItemNum;

    public RowSpacingDecoration(int spacingPx, int rowItemNum) {
        this.spacingPx = spacingPx;
        this.rowItemNum = rowItemNum;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) > (rowItemNum - 1)) {
            outRect.top = spacingPx;
        }
    }
}