package com.example.empty.myapplication.MainScreen;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.empty.myapplication.DetailsScreen.DetailActivity;
import com.example.empty.myapplication.DetailsScreen.DetailFragment;
import com.example.empty.myapplication.MapScreen.GoogleMapFragment;
import com.example.empty.myapplication.MapScreen.MapActivity;
import com.example.empty.myapplication.R;
import com.example.empty.myapplication.Route;

import java.util.List;


public class ListActivity extends AppCompatActivity implements ListFragment.OnItemClick, DetailFragment.OnItemClick {

    public static final String GOOGLEMAPFRAGMENT = "GoogleMapFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameActivity, ListFragment.newInstance()).commit();
        }

    }


    @Override
    public void onItemClickInList(Route route) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            DetailActivity.create(this, route.getId());

        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.DetailFrame, DetailFragment.newInstance(route.getId())).commit();

        }
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(GOOGLEMAPFRAGMENT);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        } else super.onBackPressed();
    }


    @Override
    public void onShowMapClick(String name, double longitude, double latitude) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            MapActivity.create(this, name, longitude, latitude);

        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.DetailFrame, GoogleMapFragment.newInstance(name, longitude, latitude), GOOGLEMAPFRAGMENT).commit();

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
