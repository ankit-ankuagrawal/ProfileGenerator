package com.example.profilegenerator.model;

import android.graphics.Bitmap;

public class ProfileModel {
    private Bitmap image;
    private String userName;
    private String passcode;

    public ProfileModel(Bitmap imageUrl, String userName, String passcode) {
        this.image = imageUrl;
        this.userName = userName;
        this.passcode = passcode;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getUserName() {
        return userName;
    }

    public String getPasscode() {
        return passcode;
    }

    public String toString()
    {
        return image.getByteCount() +" "+userName +" "+passcode;
    }
}
