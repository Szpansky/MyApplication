package com.example.empty.myapplication.LoadingData;

import com.example.empty.myapplication.RoutesList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RootApi {
    @GET("topsecret")
    Call<RoutesList> routesData();
}
