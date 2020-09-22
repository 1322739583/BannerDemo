package com.example.bannerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BannerPanelLayout banner;
    private int[] imgs=new int[]{R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner=findViewById(R.id.banner);



        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
       int width= metrics.widthPixels;

        List<ImageView> views=new ArrayList<>();

        for (int i = 0; i < imgs.length; i++) {
            ImageView imageView=new ImageView(this);
           imageView.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setImageResource(imgs[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views.add(imageView);
           // banner.addView(imageView);
        }

        banner.setImages(views);

        banner.setOnItemClickListener(new BannerPanelLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
