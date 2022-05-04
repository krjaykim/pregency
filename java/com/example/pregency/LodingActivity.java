package com.example.pregency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class LodingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
      //  startLoading();
        ImageView gif_image = (ImageView) findViewById(R.id.gif_image);
        Glide.with(this).load(R.drawable.pregencyloading).into(gif_image);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main = new Intent(LodingActivity.this, mainlogin.class);
                startActivity(main);
                finish();
            }
        },3000);
    }
   /* private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }*/
}
