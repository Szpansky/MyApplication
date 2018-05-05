package com.example.empty.myapplication.MainScreen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.empty.myapplication.Constants;
import com.example.empty.myapplication.R;
import com.example.empty.myapplication.Route;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


    OnClickRecyclerViewAdapter onClickRecyclerViewAdapter;

    private List<Route> routeSet;
    private Context context;

    public interface OnClickRecyclerViewAdapter {
        void onClick(Route route);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nameTextView)
        TextView name;
        @BindView(R.id.timeTextView)
        TextView time;
        @BindView(R.id.distanceTextView)
        TextView distance;
        @BindView(R.id.image)
        ImageView image;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRecyclerViewAdapter.onClick(routeSet.get(getAdapterPosition()));
                }
            });


        }

        void bind(Route route) {
            name.setText(String.valueOf(route.getName()));
            time.setText(String.valueOf(route.getDuration()));
            distance.setText(String.valueOf(route.getDistance()));

            Glide.with(context)
                    .load(Constants.picturesURL + route.getThumb_id())
                    .transition(DrawableTransitionOptions.withCrossFade(1000))
                    .into(image);

        }

    }


    public ListAdapter(Context context, List<Route> dataset, OnClickRecyclerViewAdapter onClickRecyclerViewAdapter) {
        routeSet = dataset;
        this.context = context;
        this.onClickRecyclerViewAdapter = onClickRecyclerViewAdapter;

    }


    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(routeSet.get(position));
    }


    @Override
    public int getItemCount() {
        return routeSet.size();
    }


}
