package com.example.empty.myapplication.LoadingData;

import com.example.empty.myapplication.Route;

import java.util.List;

public interface LoadingProvider {

    void loadData(CallBack callBack);

    interface CallBack{
        void onSuccess(List<Route> routes);
        void onFailed(Throwable t);
    }


}
