package com.example.profilegenerator.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.profilegenerator.model.ProfileModel;
import com.example.profilegenerator.util.Utility;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_PROFILE = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSCODE = "passcode";
    private static final String KEY_IMAGE = "image";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSCODE + " TEXT, " + KEY_IMAGE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void addProfile(ProfileModel profileModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, profileModel.getUserName());
        values.put(KEY_PASSCODE, profileModel.getPasscode());
        values.put(KEY_IMAGE, Utility.BitMapToString(profileModel.getImage()));

        // Inserting Row
        db.insert(TABLE_PROFILE, null, values);
        db.close(); // Closing database connection
    }

    public List<ProfileModel> getAllProfile() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROFILE, null, null, null, null, null, null);

        List<ProfileModel> profileModelList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String userName = cursor.getString(cursor.getColumnIndex(KEY_USERNAME));
                String passcode = cursor.getString(cursor.getColumnIndex(KEY_PASSCODE));
                String image = cursor.getString(cursor.getColumnIndex(KEY_IMAGE));

                profileModelList.add(new ProfileModel(Utility.StringToBitMap(image), userName, passcode));
            }
        }
        return profileModelList;
    }
}
