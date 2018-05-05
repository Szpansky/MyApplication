package com.example.empty.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPreferences {


    public static final String CASH_TIME = "CashTime";
    public static final String DEFAULT_TIME = "00:00:00";

    private UserSharedPreferences() {
    }

    static public Long getCashTime(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("global", Context.MODE_PRIVATE);
        return sharedPreferences.getLong(CASH_TIME, -1);
    }

    static public void setCashTime(Context context, Long cashTime){
        SharedPreferences sharedPreferences = context.getSharedPreferences("global", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(CASH_TIME,cashTime);
        editor.apply();
    }
}
