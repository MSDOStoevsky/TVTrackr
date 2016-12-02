package com.emptystudio.tvtrackr;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.concurrent.ExecutionException;

public class ShowDetailsActivity extends AppCompatActivity {
    AccessDatabase db;
    Show show;
    ImageView thumbnail;
    TextView name, description, genres, status, schedule;
    Button followButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details_layout);

        db = new AccessDatabase(this);
        show = (Show) getIntent().getSerializableExtra("show");

        initializeViews();
        populateViews();

        if (db.getFavorite(show) == null) {
            updateButton("follow");
            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.addFavorite(show);
                    updateButton("unfollow");
                }
            });
        }
        else {
            updateButton("unfollow");
            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.removeFavorite(show);
                    updateButton("follow");
                }
            });
        }
    }

    private void initializeViews() {
        thumbnail = (ImageView) findViewById(R.id.d_show_thumbnail);
        name = (TextView) findViewById(R.id.d_show_name);
        description = (TextView) findViewById(R.id.d_show_description);
        genres = (TextView) findViewById(R.id.d_show_genres);
        status = (TextView) findViewById(R.id.d_show_status);
        schedule = (TextView) findViewById(R.id.d_show_schedule);
        followButton = (Button) findViewById(R.id.follow_button);
    }

    private void populateViews() {
        Bitmap bmp = null;

        try {
            bmp = new DownloadImageTask().execute(show.getImageURL()).get();
        } catch (InterruptedException e) {
            Log.e("ShowDetailsActivity", "InterruptedException");
        } catch (ExecutionException e) {
            Log.e("ShowDetailsActivity", "ExecutionException");
        }

        thumbnail.setImageBitmap(bmp);
        name.setText(show.getName());
        description.setText(Html.fromHtml("<strong>Description: </strong>" + show.getDescription()));
        description.setMovementMethod(new ScrollingMovementMethod());
        genres.setText(Html.fromHtml("<strong>Genres: </strong>" + show.getGenres()));
        status.setText(Html.fromHtml("<strong>Status: </strong>" + show.getStatus()));
        String time = (show.getAirTime().equals("") ? "?" : show.getAirTime());
        schedule.setText(Html.fromHtml("<strong>Airs: </strong>" + show.getSchedule() + " at " + time));
    }

    private void updateButton(String state) throws IllegalArgumentException {
        switch (state) {
            case "follow":
                followButton.setText(R.string.buttonText_follow);
                followButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.buttonText_follow));
                followButton.setBackgroundResource(R.drawable.follow);
                break;
            case "unfollow":
                followButton.setText(R.string.buttonText_unfollow);
                followButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.buttonText_unfollow));
                followButton.setBackgroundResource(R.drawable.unfollow);
                break;
            default:
                throw new IllegalArgumentException("Invalid follow button state");
        }
    }
}
