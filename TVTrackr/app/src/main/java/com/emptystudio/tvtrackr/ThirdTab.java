package com.emptystudio.tvtrackr;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dylan on 10/14/2016.
 */
public class ThirdTab extends Fragment{

    public ThirdTab() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.schedule_tab, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.schedule_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //RecyclerView.Adapter adapter = new DataAdapter(data_set);
        //recyclerView.setAdapter(adapter);
        return view;
    }

}