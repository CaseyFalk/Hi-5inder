package com.example.casey.hi_5inder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class EditProfile extends AppCompatActivity implements View.OnClickListener{

    private Button saveProfileButton;
    private ImageView profilePic;
    private EditText editUsername;
    private FirebaseAuth firebaseAuth;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //Initialize Views
        saveProfileButton = (Button) findViewById(R.id.saveProfileButton);
        profilePic = (ImageView) findViewById(R.id.profilePicTemp);
        editUsername = (EditText) findViewById(R.id.editUserText);

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


        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user information
                        User user = dataSnapshot.getValue(User.class);

                        if (user.profilePic != null){
                            String picURL = user.profilePic;
                            //Loading image from below url into imageView
                            profilePic.setImageBitmap(base64ToBitmap(picURL));
                        }
                        else{
                            //Loading image from below url into imageView
                            Glide.with(EditProfile.this)
                                    .load("https://firebasestorage.googleapis.com/v0/b/hi-5inder.appspot.com/o/hand-1318340_960_720.png?alt=media&token=ab6dd714-28dd-4f50-a275-d6430860b761")
                                    .into(profilePic);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //set image


        saveProfileButton.setOnClickListener(this);
        profilePic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == saveProfileButton){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userStatus = database.getReference("/users/" + firebaseAuth.getUid() + "/username");
            userStatus.setValue(editUsername.getText().toString());


            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();

            finish();
            startActivity(new Intent(this, ProfileActivity.class));

        }
        else if (v == profilePic){
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePicture.resolveActivity(getPackageManager()) != null){
                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profilePic.setImageBitmap(imageBitmap);
            profilePic.setRotation(-90);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userStatus = database.getReference("/users/" + firebaseAuth.getUid() + "/profilePic");
            userStatus.setValue(bitmapToBase64(imageBitmap));

        }
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
