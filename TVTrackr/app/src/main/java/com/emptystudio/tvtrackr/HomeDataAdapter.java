package com.emptystudio.tvtrackr;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dylan on 12/2/2016.
 */
public class HomeDataAdapter extends RecyclerView.Adapter<HomeDataAdapter.ViewHolder> {

    private List<Show> shows = new ArrayList<>();

    public HomeDataAdapter(List<Show> shows) {
        this.shows = shows;
    }

    @Override
    public HomeDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Show currShow = shows.get(i);
        holder.name.setText(currShow.getName());
        holder.schedule.setText("Coming up at " + currShow.getAirTime());
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

    public void updateData(List<Show> newShows) {
        shows.clear();
        shows.addAll(newShows);
        notifyDataSetChanged();
    }

}