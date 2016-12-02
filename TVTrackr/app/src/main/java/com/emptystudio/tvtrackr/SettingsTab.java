package com.emptystudio.tvtrackr;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Dylan on 12/2/2016.
 */
public class SettingsTab extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

}
