package com.example.empty.myapplication.LoadingData;

import android.content.Context;

import com.example.empty.myapplication.Database.Database;
import com.example.empty.myapplication.Database.Tables;
import com.example.empty.myapplication.Route;
import com.example.empty.myapplication.UserSharedPreferences;

import java.util.Calendar;
import java.util.List;

public class ControlLoadingProvider implements LoadingProvider {
    Database database;
    Context context;
    LocalLoadingProvider localLoadingProvider;
    NetworkLoadingProvider networkLoadingProvider;
    public static final long CASH_TIME_DELAYS = 1000*60*5;


    public ControlLoadingProvider(Context context) {
        database = new Database(context);
        this.context = context;
         localLoadingProvider = new LocalLoadingProvider(context);
         networkLoadingProvider = new NetworkLoadingProvider();
    }

    @Override
    public void loadData(final CallBack callBack) {
        long cashTime = UserSharedPreferences.getCashTime(context);
        final long currentTime = getCurrentTime();

      if((currentTime - cashTime) > CASH_TIME_DELAYS ){
          networkLoadingProvider.loadData(new CallBack() {
              @Override
              public void onSuccess(List<Route> routes) {
                  database.clearTable(Tables.Cash.TABLE_NAME);

                   for (int i=0; i < routes.size(); i++){
                     database.putRoute(routes.get(i));
                  }

                  UserSharedPreferences.setCashTime(context,currentTime);
                   callBack.onSuccess(routes);
              }

              @Override
              public void onFailed(Throwable t) {
                callBack.onFailed(t);
              }
          });
      }else {
          localLoadingProvider.loadData(callBack);
      }

    }


    private long getCurrentTime(){
    return Calendar.getInstance().getTime().getTime();
    }



}
