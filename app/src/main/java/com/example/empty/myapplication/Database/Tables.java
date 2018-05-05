package com.example.empty.myapplication.Database;

import android.provider.BaseColumns;

public class Tables {

    private Tables() {

    }


    public static class Cash implements BaseColumns {
        public static final String TABLE_NAME = "Cash_Data";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_DISTANCE = "distance";
        public static final String COLUMN_THUMB_ID = "thumb_id";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
    }



    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Tables.Cash.TABLE_NAME + " (" +
                    Tables.Cash._ID + " INT PRIMARY KEY," +
                    Tables.Cash.COLUMN_ID + " INT," +
                    Tables.Cash.COLUMN_NAME + " TEXT," +
                    Tables.Cash.COLUMN_DURATION + " INT," +
                    Tables.Cash.COLUMN_DISTANCE + " DOUBLE," +
                    Tables.Cash.COLUMN_THUMB_ID + " TEXT," +
                    Tables.Cash.COLUMN_LATITUDE + " DOUBLE," +
                    Tables.Cash.COLUMN_LONGITUDE + " DOUBLE)";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Tables.Cash.TABLE_NAME;

}
