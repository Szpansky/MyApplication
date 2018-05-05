package com.example.empty.myapplication.MapScreen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.empty.myapplication.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String NAME = "name";
    public static final String LATLNG_LATITUDE = "latLangLatitude";
    public static final String LATLNG_LONGITUDE = "latLangLongitude";
    public static final String DRAW_STATE = "drawState";

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 44;

    DrawState drawState = DrawState.DRAW_OFF;

    double latitude, longitude;
    String name;

    private GoogleMap map;

    Polyline polyline;

    private enum DrawState {
        DRAW_ON,
        DRAW_OFF
    }

    MapViewModel mapViewModel;

    @BindView(R.id.mapViewInFragment)
    MapView mapView;

    @BindView(R.id.currentPositionImage)
    ImageView currentPositionImage;

    @BindView(R.id.polylineDrawImage)
    ImageView polylineDrawImage;


    @OnClick(R.id.polylineDrawImage)
    public void onPolylineDrawClick() {
        if (drawState.equals(DrawState.DRAW_ON)) {
            drawState = DrawState.DRAW_OFF;

        } else if (drawState.equals(DrawState.DRAW_OFF)) {
            drawState = DrawState.DRAW_ON;
        }
        setCanDraw(drawState);
    }

    @OnClick(R.id.currentPositionImage)
    public void onCurrentPositionClick() {
        checkPermissionsAndSetLocation();
    }


    public static GoogleMapFragment newInstance(String name, double latitude, double longitude) {
        Bundle args = new Bundle();
        args.putDouble(LATITUDE, latitude);
        args.putDouble(LONGITUDE, longitude);
        args.putString(NAME, name);

        GoogleMapFragment fragment = new GoogleMapFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        latitude = getArguments().getDouble(LATITUDE);
        longitude = getArguments().getDouble(LONGITUDE);
        name = getArguments().getString(NAME);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        polyline = map.addPolyline(mapViewModel.polylineOptions);
        setCanDraw(drawState);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        LatLng location = new LatLng(longitude, latitude);
        map.addMarker(new MarkerOptions().position(location).title(name));

        map.moveCamera(CameraUpdateFactory.newLatLng(location));

        map.setMinZoomPreference(10);
        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                map.resetMinMaxZoomPreference();
            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (drawState.equals(DrawState.DRAW_ON)) {
                    mapViewModel.polylineOptions.add(latLng);
                    Toast.makeText(getContext(), "click on map" + latLng.latitude + ", " + latLng.longitude, Toast.LENGTH_SHORT).show();

                    if (polyline != null) {
                        polyline.remove();
                    }
                    polyline = map.addPolyline(mapViewModel.polylineOptions);
                }
            }
        });


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DRAW_STATE, drawState);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            drawState = (DrawState) savedInstanceState.getSerializable(DRAW_STATE);
        }
    }


    private void checkPermissionsAndSetLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        } else {
            // Permission has already been granted
            setFusedLocationProviderClient();
        }
    }


    private void setCanDraw(DrawState draw) {
        switch (draw) {
            case DRAW_ON: {
                Toast.makeText(getContext(), "Rysowanie włączone", Toast.LENGTH_SHORT).show();
                polylineDrawImage.setImageResource(R.mipmap.ic_close_black_24dp);
            }
            break;
            case DRAW_OFF: {
                mapViewModel.polylineOptions = new PolylineOptions();
                if (polyline != null) {
                    polyline.remove();
                }
                Toast.makeText(getContext(), "Rysowanie wyłączone", Toast.LENGTH_SHORT).show();
                polylineDrawImage.setImageResource(R.mipmap.ic_mode_edit_black_24dp);
            }
            break;
        }

    }


    @SuppressLint("MissingPermission")
    private void setFusedLocationProviderClient() {

        LocationServices.getFusedLocationProviderClient(getContext()).getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                    map.setMinZoomPreference(10);
                    map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                        @Override
                        public void onCameraMove() {
                            map.resetMinMaxZoomPreference();
                        }
                    });
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                    map.setMyLocationEnabled(true);
                    currentPositionImage.setVisibility(View.GONE);
                } else {
                    map.setMyLocationEnabled(false);
                    map.getUiSettings().setMyLocationButtonEnabled(false);
                    currentPositionImage.setVisibility(View.VISIBLE);

                    Toast.makeText(getContext(), "Brak dostępu do lokalizacji, włącz lokalizację i spróbuj ponownie", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    setFusedLocationProviderClient();
                } else {
                    Toast.makeText(getActivity(), "Nie udzieliles zgody", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


}
