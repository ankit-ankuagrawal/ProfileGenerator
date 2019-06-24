package com.example.profilegenerator.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.profilegenerator.R;
import com.example.profilegenerator.adapter.ProfileAdapter;
import com.example.profilegenerator.loader.ProfileLoader;
import com.example.profilegenerator.model.ProfileModel;
import com.example.profilegenerator.sql.DatabaseHandler;
import com.example.profilegenerator.util.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ProfileModel>> {

    private static String LOG_CAT = MainActivity.class.getName();
    private static final int CAMERA_REQUEST = 1888;
    private static final String USER_STRING = "User ";

    private List<ProfileModel> profileModelList;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;

    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        profileModelList = new ArrayList<>();
        adapter = new ProfileAdapter(profileModelList);

        dbHandler = new DatabaseHandler(this);

        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");

            int sizeList = profileModelList.size();
            String userName = USER_STRING + (sizeList + 1);

            String passcode = "#" + passcodeGenerator(sizeList);

            ProfileModel profileModel = new ProfileModel(image, userName, passcode);

            Log.i(LOG_CAT, "Profile added successfully" + profileModel);
            profileModelList.add(profileModel);

            //adapter.notifyDataSetChanged();
            adapter.notifyItemInserted(sizeList);

            dbHandler.addProfile(profileModel);
        }
    }

    private String passcodeGenerator(int sizeList) {
        char[] digits = "0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(digits[new Random().nextInt(digits.length)]);
        }
        return stringBuilder.toString();
    }

    @Override
    public Loader<List<ProfileModel>> onCreateLoader(int i, Bundle bundle) {
        return new ProfileLoader(this, dbHandler);
    }

    @Override
    public void onLoadFinished(Loader<List<ProfileModel>> loader, List<ProfileModel> profileModels) {
        if (profileModels != null && !profileModels.isEmpty()) {
            profileModelList.clear();
            profileModelList.addAll(profileModels);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ProfileModel>> loader) {
    }
}


