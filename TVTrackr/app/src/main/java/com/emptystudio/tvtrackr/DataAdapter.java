package com.emptystudio.tvtrackr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Dylan on 10/14/2016.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<Show> shows = new ArrayList<>();
    private int expandedPosition = -1;

    public DataAdapter(List<Show> shows) {
        this.shows = shows;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {
        AsyncTask<String, Void, Bitmap> bmp = new DownloadImageTask().execute(shows.get(i).getImage());

        viewHolder.name.setText(shows.get(i).getName());
        viewHolder.schedule.setText(shows.get(i).getSchedule());

        try {
            viewHolder.thumbnail.setImageBitmap(bmp.get());
        } catch (InterruptedException e) {
            Log.e("DataAdapter", "InterruptedException");
        } catch (ExecutionException e) {
            Log.e("DataAdapter", "ExecutionException");
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder holder = (ViewHolder) v.getTag();
                //Show theShow = shows.get(holder.getAdapterPosition());

                // Check for an expanded view, collapse if you find one
                if (expandedPosition >= 0) {
                    int prev = expandedPosition;
                    notifyItemChanged(prev);
                }
                // Set the current position to "expanded"
                expandedPosition = holder.getAdapterPosition();
                notifyItemChanged(expandedPosition);

                //Toast.makeText(mContext, "Clicked: "+ theShow, Toast.LENGTH_SHORT).show();
            }
        });

        if (i == expandedPosition) {
            viewHolder.llExpandArea.setVisibility(View.VISIBLE);
        } else {
            viewHolder.llExpandArea.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView schedule;
        private ImageView thumbnail;
        private LinearLayout llExpandArea;

        public ViewHolder(View view) {
            super(view);

            name = (TextView)view.findViewById(R.id.show_name);
            schedule = (TextView)view.findViewById(R.id.show_schedule);
            thumbnail = (ImageView) view.findViewById(R.id.show_thumbnail);
            llExpandArea = (LinearLayout) view.findViewById(R.id.llExpandArea);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            Bitmap bmp = null;

            try {
                InputStream in = new URL(urls[0]).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) {
                Log.e("DataAdapter", "MalformedURLException");
            } catch (IOException e) {
                Log.e("DataAdapter", "IOException");
            }

            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            //mImageView.setImageBitmap(result);
        }
    }
}