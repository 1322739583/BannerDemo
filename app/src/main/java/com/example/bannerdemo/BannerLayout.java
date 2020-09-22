package com.example.bannerdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * banner主要显示内容，不一定只显示图片，支持添加自定义View
 */
public class BannerLayout extends ViewGroup {

    private int childCount;
    private int childWidth;
    private int childHeight;
    private int x;
    private int index;

    private boolean isAuto=true;
    private Timer timer = new Timer();
    private TimerTask task;
   @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    if (++index>=childCount){
                        index=0;
                    }
                    scrollTo(index*childWidth,0);
                    if (indicatorListener!=null){
                        indicatorListener.onIndicatorChange(index);
                    }
                    postInvalidate();
                    break;
            }
        }
    };

    private OnItemClickListener itemClickListener;
    private OnIndicatorChangeListener indicatorListener;
    private boolean isClick=false;





    interface OnItemClickListener{
       void onItemClick(int position);
   }

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

   interface OnIndicatorChangeListener{
        void onIndicatorChange(int position);
   }

    public OnIndicatorChangeListener getIndicatorListener() {
        return indicatorListener;
    }

    public void setIndicatorListener(OnIndicatorChangeListener indicatorListener) {
        this.indicatorListener = indicatorListener;
    }



    public BannerLayout(Context context) {
        super(context);
        init();
    }


    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        task = new TimerTask() {
            @Override
            public void run() {
                if (isAuto) {
                    handler.sendEmptyMessage(0);
                }
            }
        };
        timer.schedule(task, 100, 3000);
    }

    public void startAuto(){
        isAuto=true;
    }

    public void stopAuto(){
        isAuto=false;
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
                int left = childWidth * i;
                int right = left + childWidth;
                int top = 0;
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
                isClick=true;
                stopAuto();
                x = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                isClick=false;
                int moveX = (int) event.getX();
                int distance = moveX - x;
                scrollBy(-distance, 0);
                x = moveX;
                break;
            case MotionEvent.ACTION_UP:

                int scrollX = getScrollX();
                index = (scrollX + childWidth / 2) / childWidth;
                if (isClick&&itemClickListener!=null){
                        itemClickListener.onItemClick(index);
                }

                if (index < 0) {
                    index = 0;
                } else if (index > childCount - 1) {
                    index = childCount - 1;
                }
                scrollTo(childWidth * index, 0);
                startAuto();
                if (indicatorListener!=null){
                    indicatorListener.onIndicatorChange(index);
                }
                postInvalidate();
                break;
            default:
                break;
        }
        return true;
    }
}
