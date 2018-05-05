package com.example.empty.myapplication.LoadingData;

import android.os.Handler;

import com.example.empty.myapplication.Route;

import java.util.ArrayList;
import java.util.List;

public class TestLoadingProvider implements LoadingProvider{


    @Override
    public void loadData(final CallBack callBack) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {


            @Override
            public void run() {
                List<Route> data = new ArrayList<>();
                data.add(new Route(123,"Basia",123,23.3,"123",2.13,23.12));
                data.add(new Route(123,"Basia",123,23.3,"123",2.13,23.12));
                data.add(new Route(123,"Basia",123,23.3,"123",2.13,23.12));
                data.add(new Route(123,"Basia",123,23.3,"123",2.13,23.12));

                int random = (int)( Math.random() * 100);
                if(random % 2 == 0) {
                    callBack.onSuccess(data);
                }else {
                    callBack.onFailed(new RuntimeException());
                }

            }



        },1000);
    }
}
