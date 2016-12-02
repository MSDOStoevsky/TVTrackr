package com.emptystudio.tvtrackr;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dylan on 10/14/2016.
 */
public class ScheduleDataAdapter extends RecyclerView.Adapter<ScheduleDataAdapter.ViewHolder> {

    private List<Show> shows = new ArrayList<>();

    public ScheduleDataAdapter(List<Show> shows) {
        this.shows = shows;
    }

    @Override
    public ScheduleDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleDataAdapter.ViewHolder holder, int i) {
        Show currShow = shows.get(i);
        holder.name.setText(currShow.getName());
        holder.schedule.setText(currShow.getSchedule() + " " + currShow.getAirTime());
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