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

        holder.name.setText(shows.get(i).getName());
        holder.schedule.setText(Html.fromHtml(shows.get(i).getAirTime()));
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
        private TextView schedule;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            name = (TextView)view.findViewById(R.id.show_name);
            schedule = (TextView)view.findViewById(R.id.show_schedule);
        }

        // Clicking on a card will start a new activity which displays the selected show in
        // more detail as well as the follow button
        @Override
        public void onClick(View v) { }
    }

    public void updateData(List<Show> newShows) {
        shows.clear();
        shows.addAll(newShows);
        notifyDataSetChanged();
    }

}