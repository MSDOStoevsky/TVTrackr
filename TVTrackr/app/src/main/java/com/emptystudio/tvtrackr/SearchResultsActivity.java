package com.emptystudio.tvtrackr;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchResultsActivity extends AppCompatActivity {
    private AsyncTask<String, Void, JSONArray> searchResults = null;
    private List<Show> shows = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new DataAdapter(shows);
        mRecyclerView.setAdapter(mAdapter);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchResults = doMySearch(query);
        }

        try {
            search(searchResults.get());
            //displayResults(shows);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public AsyncTask<String, Void, JSONArray> doMySearch(String query) {
        return new AccessWebsite(getBaseContext(), findViewById(R.id.root)).execute(query);
    }

    /*
        parses json array from AccessWebsite
     */
    private void search(JSONArray jayson) {
        if (jayson != null && jayson.length() != 0) {
            for (int i = 0; i < jayson.length(); i++) {
                try {
                    JSONObject ob = jayson.getJSONObject(i);
                    JSONObject show = ob.getJSONObject("show");
                    JSONArray genresArray = show.getJSONArray("genres");
                    List<String> genres = new ArrayList<>();

                    for (int j = 0; j < genresArray.length(); j++) {
                        genres.add(genresArray.getString(j));
                    }

                    String name = show.getString("name");
                    String schedule = show.getString("schedule");
                    String image = show.getJSONObject("image").getString("medium");

                    Show current = new Show(name, genres, schedule, image);
                    shows.add(current);
                } catch (JSONException e) {
                    Toast.makeText(SearchResultsActivity.this, "That's not supposed to happen.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
/*
    public void displayResults(ArrayList<Show> results) {
        if (results.isEmpty()) {
            Toast.makeText(SearchResultsActivity.this, "There was an error when parsing the JSON array!", Toast.LENGTH_SHORT).show();
        }

        try {
            myText.setText(shows.toString());
        } catch (Exception e) {

        }
    }*/
}
