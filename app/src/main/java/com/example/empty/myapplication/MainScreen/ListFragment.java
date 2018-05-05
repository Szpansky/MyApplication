package com.example.empty.myapplication.MainScreen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.empty.myapplication.LoadingData.ControlLoadingProvider;
import com.example.empty.myapplication.LoadingData.LoadingProvider;
import com.example.empty.myapplication.R;
import com.example.empty.myapplication.Route;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ListFragment extends Fragment {
    OnItemClick onItemClick;

    public interface OnItemClick {
        void onItemClickInList(Route route);
    }


    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.error_layout)
    RelativeLayout errorLayout;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @OnClick(R.id.refresh_button_dialog)
    public void setRefreshButtonDialog() {
        errorLayout.setVisibility(View.GONE);
        loadData();
    }



    private LoadingProvider loadingProvider;


    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();

            }

        });

        loadingProvider = new ControlLoadingProvider(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();

        return view;
    }


    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);

        loadingProvider.loadData(new LoadingProvider.CallBack() {
            @Override
            public void onSuccess(List<Route> routes) {
                ListAdapter.OnClickRecyclerViewAdapter recycleViewListener = new ListAdapter.OnClickRecyclerViewAdapter() {
                    @Override
                    public void onClick(Route route) {
                        onItemClick.onItemClickInList(route);
                    }
                };
                recyclerView.setAdapter(new ListAdapter(getContext(), routes, recycleViewListener));

                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                errorLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(Throwable t) {
                Toast.makeText(getContext(), "brak danych", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                errorLayout.setVisibility(View.VISIBLE);

            }

        });

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            onItemClick = (OnItemClick) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "Must implement OnItemClick Interface from ListFragment");
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tab_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refreshMenuButton: {
                loadData();
                break;
            }
            case R.id.backMenuButton:{
            getActivity().onBackPressed();
                }
        }

        return super.onOptionsItemSelected(item);
    }


}
