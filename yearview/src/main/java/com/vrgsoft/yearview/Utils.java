package com.vrgsoft.yearview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

public class Utils {
    public static int getColumnWidth(Context context) {
        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return 250;
            case DisplayMetrics.DENSITY_MEDIUM:
                return 250;
            case DisplayMetrics.DENSITY_HIGH:
                return 250;
            case DisplayMetrics.DENSITY_XHIGH:
                return 250;
            case DisplayMetrics.DENSITY_XXHIGH:
                return 350;
            case DisplayMetrics.DENSITY_XXXHIGH:
                return 520;
            default:
                return 350;
        }
    }

    public static int getXInWindow(View v) {
        int[] coords = new int[2];
        v.getLocationInWindow(coords);
        return coords[0];
    }
    public static int getXInWindow(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;

    }
}
