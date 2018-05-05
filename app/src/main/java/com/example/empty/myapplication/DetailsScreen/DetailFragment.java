package com.example.empty.myapplication.DetailsScreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.empty.myapplication.Constants;
import com.example.empty.myapplication.R;
import com.example.empty.myapplication.Route;
import com.example.empty.myapplication.LoadingData.ControlLoadingProvider;
import com.example.empty.myapplication.LoadingData.LoadingProvider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailFragment extends Fragment {

    public static final String ROUTE_ID = "Route";

    int routeId;
    double routeLongitude;
    double routeLatitude;
    String routeName;

    OnItemClick onItemClick;

    private LoadingProvider loadingProvider;


    @BindView(R.id.nameTextView)
    TextView name;
    @BindView(R.id.timeTextView)
    TextView time;
    @BindView(R.id.distanceTextView)
    TextView distance;
    @BindView(R.id.thumnail)
    ImageView thumbnail;

    @OnClick(R.id.showOnMapTextView)
    public void showMap(){
        onItemClick.onShowMapClick(routeName,routeLongitude,routeLatitude);
    }


    public interface OnItemClick {
        void onShowMapClick(String name, double longitude, double latitude);
    }


    public static DetailFragment newInstance(int routeId) {
        Bundle args = new Bundle();
        args.putInt(ROUTE_ID,routeId);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            onItemClick = (OnItemClick) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()+"Need implement method onItemClick from DetailFragment");
        }
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,container,false);

        ButterKnife.bind(this,view);

        routeId = getArguments().getInt(ROUTE_ID, -1);

        loadingProvider = new ControlLoadingProvider(getContext());
        loadData();


        return view;
    }


    private void loadData() {
     loadingProvider.loadData(new LoadingProvider.CallBack() {
         @Override
         public void onSuccess(List<Route> routes) {
             for (Route route : routes) {
                 if(route.getId() == routeId){
                     setRoute(route);
                     routeLatitude = route.getLatitude();
                     routeLongitude = route.getLongitude();
                     routeName = route.getName();

                 }
             }
         }

         @Override
         public void onFailed(Throwable t) {

         }
     });
    }


    private void setRoute(Route route){
        name.setText(String.valueOf(route.getName()));
        time.setText(String.valueOf(route.getDuration()));
        distance.setText(String.valueOf(route.getDistance()));

        Glide.with(getContext())
                .load(Constants.picturesURL +route.getThumb_id())
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .into(thumbnail);
    }
}
