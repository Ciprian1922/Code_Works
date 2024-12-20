package com.example.students;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "friends.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_FRIENDS_TABLE =
            "CREATE TABLE " + com.example.lab7.DBContract.Friends.TABLE_NAME + " (" +
                    com.example.lab7.DBContract.Friends._ID + " INTEGER PRIMARY KEY," +
                    com.example.lab7.DBContract.Friends.COLUMN_NAME + " TEXT," +
                    com.example.lab7.DBContract.Friends.COLUMN_BIRTHDAY + " TEXT," +
                    com.example.lab7.DBContract.Friends.COLUMN_ADDRESS + " TEXT)";

    private static final String SQL_DELETE_FRIENDS_TABLE =
            "DROP TABLE IF EXISTS " + com.example.lab7.DBContract.Friends.TABLE_NAME;

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