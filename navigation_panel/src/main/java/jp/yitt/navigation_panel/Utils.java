package jp.yitt.navigation_panel;

import android.content.Context;

public class Utils {

    public static int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}