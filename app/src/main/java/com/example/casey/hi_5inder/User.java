package com.example.casey.hi_5inder;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String profilePic;
    public String status;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String profilePicUrl, String status) {
        this.username = username;
        this.email = email;
        this.profilePic = profilePicUrl;
        this.status = status;
    }

}
