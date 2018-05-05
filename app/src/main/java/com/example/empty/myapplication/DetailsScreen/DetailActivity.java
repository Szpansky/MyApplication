package com.example.empty.myapplication.DetailsScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.empty.myapplication.MapScreen.MapActivity;
import com.example.empty.myapplication.R;

public class DetailActivity extends AppCompatActivity implements DetailFragment.OnItemClick {

    public static final String ROUTE_ID = "RouteID";

    int routeId;


    public static void create(Context context, int id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ROUTE_ID, id);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        routeId = getIntent().getIntExtra(ROUTE_ID, -1);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameActivity2, DetailFragment.newInstance(routeId)).commit();
        }

    }


    @Override
    public void onShowMapClick(String name, double longitude, double latitude) {
        MapActivity.create(this, name, longitude, latitude);

    }
}
