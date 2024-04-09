package com.example.lab7;

import android.provider.BaseColumns;

public final class DBContract {

    private DBContract() {}

    public static class Friends implements BaseColumns {
        public static final String TABLE_NAME = "friends";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BIRTHDAY = "birthday";
        public static final String COLUMN_ADDRESS = "address";
    }
}