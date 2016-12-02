package com.emptystudio.tvtrackr;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dylan on 10/14/2016.
 */
public class FirstTab extends Fragment{

    private AccessDatabase db;
    private List<Show> today_data_set;
    private Context context;

    TextView noToday;
    TextView noFeatured;
    RecyclerView todayRecyclerView;
    RecyclerView featuredRecyclerView;
    RecyclerView.LayoutManager todaylayoutManager;
    RecyclerView.LayoutManager featuredlayoutManager;
    RecyclerView.Adapter adapter;

    public FirstTab() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new AccessDatabase(context);
        today_data_set = db.getTodaysSchedule();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_tab, container, false);
        todayRecyclerView = (RecyclerView) view.findViewById(R.id.home_today_recycler);
        //featuredRecyclerView = (RecyclerView) view.findViewById(R.id.home_featured_recycler);
        //todayRecyclerView.setHasFixedSize(true);
        //featuredRecyclerView.setHasFixedSize(true);

        todaylayoutManager = new LinearLayoutManager(getActivity());
        //featuredlayoutManager = new LinearLayoutManager(getActivity());

        todayRecyclerView.setLayoutManager(todaylayoutManager);
        //featuredRecyclerView.setLayoutManager(featuredlayoutManager);

        adapter = new HomeDataAdapter(today_data_set);
        todayRecyclerView.setAdapter(adapter);

        noToday = (TextView) view.findViewById(R.id.today_empty);
        noFeatured = (TextView) view.findViewById(R.id.featured_empty);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (today_data_set.isEmpty()) {
            noToday.setVisibility(View.VISIBLE);
            noFeatured.setVisibility(View.VISIBLE);
        }
        else {
            noToday.setVisibility(View.GONE);
            noFeatured.setVisibility(View.VISIBLE);
        }
    }

}