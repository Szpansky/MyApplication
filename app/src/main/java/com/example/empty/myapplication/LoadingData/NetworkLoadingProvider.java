package com.example.empty.myapplication.LoadingData;

import com.example.empty.myapplication.Constants;
import com.example.empty.myapplication.RoutesList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkLoadingProvider implements LoadingProvider {


    @Override
    public void loadData(final CallBack callBack) {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.siteURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    RootApi route =retrofit.create(RootApi.class);

    route.routesData().enqueue(new Callback<RoutesList>() {
        @Override
        public void onResponse(Call<RoutesList> call, Response<RoutesList> response) {
            if (response.isSuccessful()){
                callBack.onSuccess(response.body().getRoutes());
                System.out.println("successs");
            }
        }

        @Override
        public void onFailure(Call<RoutesList> call, Throwable t) {
            callBack.onFailed(t);
            System.out.println("faill"+t.getMessage());
        }
    });



    }
}
