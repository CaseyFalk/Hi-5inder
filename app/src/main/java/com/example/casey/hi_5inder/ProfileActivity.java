package com.example.casey.hi_5inder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button searchButton;
    private EditText statusUpdate;
    private ImageButton sendButton;
    private ImageView profileImage;
    private TextView username;
    String url = "https://firebasestorage.googleapis.com/v0/b/hi-5inder.appspot.com/o/hand-1318340_960_720.png?alt=media&token=ab6dd714-28dd-4f50-a275-d6430860b761";

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initialize ImageView
        profileImage = (ImageView) findViewById(R.id.imageView3);
        searchButton = (Button) findViewById(R.id.searchButton);
        statusUpdate = (EditText) findViewById(R.id.statusEdit);
        sendButton = (ImageButton) findViewById(R.id.sendButton);
        username = (TextView) findViewById(R.id.userTextView);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();



        //Loading image from below url into imageView
        Glide.with(this)
                .load(url)
                .into(profileImage);

        

        searchButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        profileImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == searchButton){
            finish();
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, Search.class));
        }

        else if(v == sendButton){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userStatus = database.getReference(firebaseAuth.getUid() + "/status");
            userStatus.setValue(statusUpdate.getText().toString());
        }

        else if (v == profileImage){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference pic = database.getReference(firebaseAuth.getUid() + "/profilePic");
            pic.setValue(url);
        }
    }
}
