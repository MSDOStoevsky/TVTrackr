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

import java.util.List;

/**
 * Created by Dylan on 10/14/2016.
 */
public class SecondTab extends Fragment {

    private List<Show> data_set;
    private Context context;
    private AccessDatabase db;

    TextView noResults;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    public SecondTab() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.favorites_tab, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fav_recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FavoritesDataAdapter(data_set, context);
        recyclerView.setAdapter(adapter);

        noResults = (TextView) view.findViewById(R.id.fav_empty);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFavorites();

        if (data_set.isEmpty()) {
            noResults.setVisibility(View.VISIBLE);
        }
        else {
            noResults.setVisibility(View.GONE);
        }
    }

    public void updateFavorites() {
        setDataList(db.getAllFavorites());
        ((FavoritesDataAdapter) adapter).updateData(data_set);
    }

    /****/
    public void setDataList(List<Show> list) {
        data_set = list;
    }

}