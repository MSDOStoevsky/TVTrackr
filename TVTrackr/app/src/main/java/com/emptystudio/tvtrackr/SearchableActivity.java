package com.emptystudio.tvtrackr;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Collin on 10/21/2016.
 *
 * This activity performs the search and displays the data or whatever
 */
public class SearchableActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            new AccessWebsite(getBaseContext(), findViewById(R.id.root)).execute(query);
            // doMySearch(query);
        }
    }
}
