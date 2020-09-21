package com.example.bannerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class BannerLayout extends ViewGroup {

    private int childCount;
    private int childWidth;
    private int childHeight;
    private int x;
    private int index;

    public BannerLayout(Context context) {
        super(context);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        childCount = getChildCount();
        if (0 == childCount) {
            setMeasuredDimension(0, 0);
        } else {
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            childWidth = getChildAt(0).getMeasuredWidth();
            childHeight = getChildAt(0).getMeasuredHeight();
            int width = childWidth * childCount;
            int height = childHeight;
            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                int left=childWidth*i;
                int right =left+childWidth;
                int top=0;
                int bottom = top + childHeight;
                childView.layout(left, top, right, bottom);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x= (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX= (int) event.getX();
                int distance=moveX-x;
                scrollBy(-distance,0);
                x=moveX;
                break;
            case MotionEvent.ACTION_UP:
                int scrollX=getScrollX();
                 index=(scrollX+childWidth/2)/childWidth;
                if (index<0){
                    index=0;
                }else if (index>childCount-1){
                    index=childCount-1;
                }
                scrollTo(childWidth*index,0);
                break;
            default:
                break;
        }
        return true;
    }
}
