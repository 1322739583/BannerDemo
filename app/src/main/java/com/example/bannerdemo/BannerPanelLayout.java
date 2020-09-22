package com.example.bannerdemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * banner面板类，用于加载显示的内容和指示器
 */
public class BannerPanelLayout extends FrameLayout {

    private BannerLayout bannerLayout;
    private LinearLayout linearLayout;

    public BannerPanelLayout(@NonNull Context context) {
        super(context);
        init();
    }



    public BannerPanelLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerPanelLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        loadBannerLayout();
        loadIndicatorLayout();
    }


    /**
     * 加载图片视图
     */
    private void loadBannerLayout() {
        bannerLayout = new BannerLayout(getContext());
        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        bannerLayout.setLayoutParams(lp);
        addView(bannerLayout);
    }

    /**
     * 加载指示器
     */
    private void loadIndicatorLayout() {
        linearLayout = new LinearLayout(getContext());
        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 40);
        linearLayout.setLayoutParams(lp);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(Color.DKGRAY);
        addView(linearLayout);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.gravity=Gravity.BOTTOM;
        linearLayout.setLayoutParams(layoutParams);
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB){
            linearLayout.setAlpha(0.5f);
        }else {
            linearLayout.getBackground().setAlpha(100);
        }




    }

    public void setImages(List<View> views) {
        for (int i = 0; i < views.size(); i++) {
            bannerLayout.addView(views.get(i));
            addIndicator();
        }
    }

    private void addIndicator() {
        ImageView imageView=new ImageView(getContext());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5,5,5,5);
        imageView.setLayoutParams(lp);
        imageView.setImageResource(R.drawable.dot_normal);
        linearLayout.addView(imageView);
    }
}
