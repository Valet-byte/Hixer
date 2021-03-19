package com.abyte.valet.testan40121.db;

import android.provider.BaseColumns;

public class Contracts {
    private Contracts(){}
    public static class PersonContract implements BaseColumns {
        public static final String TABLE_NAME = "personInfo";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PASSWORD = "password";
        public static final int DB_VERSION = 2;
    }
}