package com.gihub.digitspagerindicator.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

public class ScreenUtil {

    public static int dp2px(Context context, float dp) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static Rect getViewRect(View view){
        //获取标题栏高度
        Rect frame = new Rect();
        ((Activity)view.getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

        //获取View在屏幕中的top
        Rect viewRect = new Rect();
        view.getGlobalVisibleRect (viewRect) ;

        Rect result = new Rect();
        result.left = viewRect.left;
        result.right = viewRect.right;
        result.top = viewRect.top - frame.top;
        result.bottom = viewRect.bottom - frame.top;
        return result;
    }

    /**
     * 获取屏幕宽度
     *
     * @param
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
}
