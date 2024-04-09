package com.example.lab7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "friends.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_FRIENDS_TABLE =
            "CREATE TABLE " + DBContract.Friends.TABLE_NAME + " (" +
                    DBContract.Friends._ID + " INTEGER PRIMARY KEY," +
                    DBContract.Friends.COLUMN_NAME + " TEXT," +
                    DBContract.Friends.COLUMN_BIRTHDAY + " TEXT," +
                    DBContract.Friends.COLUMN_ADDRESS + " TEXT)";

    private static final String SQL_DELETE_FRIENDS_TABLE =
            "DROP TABLE IF EXISTS " + DBContract.Friends.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FRIENDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_FRIENDS_TABLE);
        onCreate(db);
    }
}