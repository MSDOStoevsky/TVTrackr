package com.emptystudio.tvtrackr;

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
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<Show> shows;

    public DataAdapter(List<Show> shows) {
        this.shows = shows;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.name.setText(shows.get(i).getName());
        viewHolder.schedule.setText(shows.get(i).getSchedule());
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView schedule;
        public ViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.show_name);
            schedule = (TextView)view.findViewById(R.id.show_schedule);
        }
    }

}