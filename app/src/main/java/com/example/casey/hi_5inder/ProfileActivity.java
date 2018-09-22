package com.example.casey.hi_5inder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initialize ImageView
        ImageView imageView3 = (ImageView) findViewById(R.id.imageView);

        //Loading image from below url into imageView
        Glide.with(this)
                .load("gs://hi-5inder.appspot.com/036.png")
                .into(imageView3);

    }
}
