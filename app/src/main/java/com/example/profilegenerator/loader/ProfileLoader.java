package com.example.profilegenerator.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import com.example.profilegenerator.model.ProfileModel;
import com.example.profilegenerator.sql.DatabaseHandler;

import java.util.List;

public class ProfileLoader extends AsyncTaskLoader<List<ProfileModel>> {


    private DatabaseHandler dbHandler;

    public ProfileLoader(Context context, DatabaseHandler dbHandler) {
        super(context);
        this.dbHandler = dbHandler;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ProfileModel> loadInBackground() {
        return dbHandler.getAllProfile();
    }
}
