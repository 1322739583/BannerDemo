package com.example.bannerdemo;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public  class ScreenUtil {
    public static int getScreenWidth(Activity activity){
        DisplayMetrics metrics=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width= metrics.widthPixels;
        return width;
    }
}
