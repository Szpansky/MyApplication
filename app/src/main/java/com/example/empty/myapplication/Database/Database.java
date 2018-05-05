package com.example.empty.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.empty.myapplication.Route;

import java.util.ArrayList;
import java.util.List;


public class Database extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CashData.db";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(Tables.SQL_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Tables.SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public boolean putRoute(Route route){

        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables.Cash.COLUMN_ID, route.getId());
        contentValues.put(Tables.Cash.COLUMN_NAME, route.getName());
        contentValues.put(Tables.Cash.COLUMN_DURATION, route.getDuration());
        contentValues.put(Tables.Cash.COLUMN_DISTANCE, route.getDistance());
        contentValues.put(Tables.Cash.COLUMN_THUMB_ID, route.getThumb_id());
        contentValues.put(Tables.Cash.COLUMN_LATITUDE, route.getLatitude());
        contentValues.put(Tables.Cash.COLUMN_LONGITUDE, route.getLongitude());

        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.insert(Tables.Cash.TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public List<Route> getRoutes(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ Tables.Cash.TABLE_NAME,null);
        if(c!=null){
            c.moveToFirst();
        }

        List<Route> routes = new ArrayList<>();

        if (c.moveToFirst()){
            do{
                int id = c.getInt(c.getColumnIndex(Tables.Cash.COLUMN_ID));
                String name = c.getString(c.getColumnIndex(Tables.Cash.COLUMN_NAME));
                double distance = c.getDouble(c.getColumnIndex(Tables.Cash.COLUMN_DISTANCE));
                int duration = c.getInt(c.getColumnIndex(Tables.Cash.COLUMN_DURATION));
                String thumb_id = c.getString(c.getColumnIndex(Tables.Cash.COLUMN_THUMB_ID));
                double latitude = c.getDouble(c.getColumnIndex(Tables.Cash.COLUMN_LATITUDE));
                double longitude = c.getDouble(c.getColumnIndex(Tables.Cash.COLUMN_LONGITUDE));
                routes.add(new Route(id,name,duration,distance,thumb_id,latitude,longitude));
            }while(c.moveToNext());
        }
        c.close();

    return routes;
    }


    public void clearTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + tableName);
    }

}
