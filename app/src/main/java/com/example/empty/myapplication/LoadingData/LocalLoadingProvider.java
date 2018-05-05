package com.example.empty.myapplication.LoadingData;


import android.content.Context;

import com.example.empty.myapplication.Database.Database;

public class LocalLoadingProvider implements LoadingProvider {

    Database database;

    public LocalLoadingProvider(Context context) {
        database = new Database(context);
    }

    @Override
    public void loadData(CallBack callBack) {

        callBack.onSuccess(database.getRoutes());

    }
}
