package com.emptystudio.tvtrackr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Dylan on 10/14/2016.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context context;
    private List<Show> shows = new ArrayList<>();

    public DataAdapter(List<Show> shows, Context context) {
        this.shows = shows;
        this.context = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {
        Bitmap bmp = null;

        try {
            bmp = new DownloadImageTask().execute(shows.get(i).getImageURL()).get();
        } catch (InterruptedException e) {
            Log.e("SearchDataAdapter", "InterruptedException");
        } catch (ExecutionException e) {
            Log.e("SearchDataAdapter", "ExecutionException");
        }

        viewHolder.thumbnail.setImageBitmap(bmp);
        viewHolder.name.setText(shows.get(i).getName());
        viewHolder.description.setText(Html.fromHtml(shows.get(i).getDescription()));
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView thumbnail;
        private TextView name;
        private TextView description;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            thumbnail = (ImageView)view.findViewById(R.id.show_thumbnail);
            name = (TextView)view.findViewById(R.id.show_name);
            description = (TextView)view.findViewById(R.id.show_description);
        }

        // Clicking on a card will start a new activity which displays the selected show in
        // more detail as well as the follow button
        @Override
        public void onClick(View v) {
            Show show = shows.get(getAdapterPosition());
            Intent intent = new Intent(context, ShowDetailsActivity.class);

            // Send the show to the show details page
            intent.putExtra("show", show);

            context.startActivity(intent);
        }
    }

}