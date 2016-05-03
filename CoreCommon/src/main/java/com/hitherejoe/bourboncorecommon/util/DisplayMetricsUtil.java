package com.hitherejoe.bourboncorecommon.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public final class DisplayMetricsUtil {

    /**
     * Convert pixels to DP using the device screen density
     */
    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return pxToDp(px, densityDpi);
    }

    /**
     * Convert pixels to DP using a given density from {@link DisplayMetrics}
     */
    public static float pxToDp(float px, float densityDpi) {
        return px / (densityDpi / 160f);
    }

    /**
     * Convert DP to pixels using the device screen density
     */
    public static int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    /**
     * Convert SP to pixels using the device screen density
     */
    public static float spToPx(float sp) {
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return Math.round(sp * scaledDensity);
    }

    /**
     * Return true if the smallest width in DP of the device is equal or greater than the given
     * value.
     */
    public static boolean isScreenSw(int smallestWidthDp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float widthDp = displayMetrics.widthPixels / displayMetrics.density;
        float heightDp = displayMetrics.heightPixels / displayMetrics.density;
        float screenSw = Math.min(widthDp, heightDp);
        return screenSw >= smallestWidthDp;
    }

    /**
     * Return true if the width in DP of the device is equal or greater than the given value
     */
    public static boolean isScreenW(int widthDp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        return screenWidth >= widthDp;
    }
}