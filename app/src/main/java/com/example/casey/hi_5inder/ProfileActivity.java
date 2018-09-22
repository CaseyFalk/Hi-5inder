package com.example.casey.hi_5inder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initialize ImageView
        ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
        searchButton = (Button) findViewById(R.id.searchButton);

        //Loading image from below url into imageView
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/hi-5inder.appspot.com/o/036.png?alt=media&token=40e61dcc-e5f2-48e3-9136-405281fa642c")
                .into(imageView3);

        searchButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == searchButton){
            finish();
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, Search.class));
        }
    }
}
