package com.emptystudio.tvtrackr;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Dylan on 12/2/2016.
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsTab())
                .commit();
    }
}