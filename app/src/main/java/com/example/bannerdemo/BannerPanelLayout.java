package com.example.bannerdemo;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

/**
 * banner面板类，用于加载显示的内容和指示器
 */
public class BannerPanelLayout extends FrameLayout implements BannerLayout.OnIndicatorChangeListener , BannerLayout.OnItemClickListener {



    private BannerLayout bannerLayout;
    private LinearLayout linearLayout;
    private List<ImageView> contentViews;



    private BannerPanelLayout.OnItemClickListener onItemClickListener;

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
        bannerLayout.setIndicatorListener(this);
        bannerLayout.setItemClickListener(this);
    }

    interface OnItemClickListener{
        void onItemClick(int position);
    }

    public BannerPanelLayout.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(BannerPanelLayout.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BannerLayout getBannerLayout() {
        return bannerLayout;
    }

    public void setBannerLayout(BannerLayout bannerLayout) {
        this.bannerLayout = bannerLayout;
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
//        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB){
//          linearLayout.
  //      }else {
            //使用这个方法就行，在api16是生效的，不影响子View的透明度
            linearLayout.getBackground().mutate().setAlpha(100);
      //  }




    }

    public void setImages(List<ImageView> views) {
        for (int i = 0; i < views.size(); i++) {
            bannerLayout.addView(views.get(i));
            addIndicator();
        }
        contentViews=views;
    }

    private void addIndicator() {
        ImageView imageView=new ImageView(getContext());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5,5,5,5);
        imageView.setLayoutParams(lp);
        imageView.setImageResource(R.drawable.dot_normal);
        linearLayout.addView(imageView);
    }

    @Override
    public void onIndicatorChange(int position) {
        Log.d("BannerPanelLayout", "position:" + position);
        for (int i = 0; i <contentViews.size() ; i++) {

            ImageView  child = (ImageView) linearLayout.getChildAt(i);
            if (i==position){
                child.setImageResource(R.drawable.dot_selected);
            }else {
                child.setImageResource(R.drawable.dot_normal);
            }
         //   child.invalidate();
          //  child.postInvalidate();
        }
      //  linearLayout.postInvalidate();
    }

    @Override
    public void onItemClick(int position) {
        if (onItemClickListener!=null) {
            onItemClickListener.onItemClick(position);
        }
    }
}
