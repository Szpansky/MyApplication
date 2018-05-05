package com.example.empty.myapplication.MapScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.empty.myapplication.R;

import java.util.List;

public class MapActivity extends AppCompatActivity {

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String NAME = "name";

    double latitude, longitude;
    String name;


    public static void create(Context context, String name, double latitude, double longitude) {
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        intent.putExtra(NAME, name);

        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        longitude = getIntent().getDoubleExtra(LONGITUDE, 0);
        latitude = getIntent().getDoubleExtra(LATITUDE, 0);
        name = getIntent().getStringExtra(NAME);



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mapFrame, GoogleMapFragment.newInstance(name,latitude, longitude)).commit();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }


}
