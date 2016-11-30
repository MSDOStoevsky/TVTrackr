package com.emptystudio.tvtrackr;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchResultsActivity extends AppCompatActivity {
    private JSONArray searchResults = null;
    private List<Show> shows = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new SearchDataAdapter(shows, this);
        mRecyclerView.setAdapter(mAdapter);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            try {
                searchResults = doMySearch(query).get();
            } catch (InterruptedException e) {
                Log.e("SearchResultsActivity", "InterruptedException");
            } catch (ExecutionException e) {
                Log.e("SearchResultsActivity", "ExecutionException");
            }
        }

        // Parse all the JSONObjects and turn them into Shows
        search(searchResults);

        if (shows.isEmpty()) {
            TextView noResults = (TextView) findViewById(R.id.no_results);
            noResults.setVisibility(View.VISIBLE);
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
                    JSONObject schedule = show.getJSONObject("schedule");

                    String name = show.getString("name");
                    String description = show.getString("summary");
                    List<String> genres = parseJSONArray(show.getJSONArray("genres"));
                    List<String> days = parseJSONArray(schedule.getJSONArray("days"));
                    String airTime = schedule.getString("time");
                    String status = show.getString("status");
                    String imageURL = show.getJSONObject("image").getString("medium");

                    if (description.isEmpty()) {
                        description = "<em>No description available.</em>";
                    }

                    Show current = new Show(name, description, genres, days, airTime, status, imageURL);
                    shows.add(current);
                } catch (JSONException e) {
                    Log.e("SearchResultsActivity", "JSONException");
                }
            }
        }
    }

    private List<String> parseJSONArray(JSONArray arr) {
        List<String> ret = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            try {
                ret.add(arr.getString(i));
            } catch (JSONException e) {
                Log.e("SearchResultsActivity", "JSONException");
            }
        }

        if (ret.isEmpty()) {
            ret.add("<em>N/A</em>");
        }

        return ret;
    }
}
