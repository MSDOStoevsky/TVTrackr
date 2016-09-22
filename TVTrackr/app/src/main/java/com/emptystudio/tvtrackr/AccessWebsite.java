package com.emptystudio.tvtrackr;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dylan on 9/21/2016.
 */
public class AccessWebsite extends AsyncTask<String, Void, JSONObject>{
    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            URL url = new URL("http://api.tvmaze.com/search/shows?q=" + params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                bufferedReader.close();
            } finally {
                urlConnection.disconnect();
            }
        } catch(Exception e) {
            Log.e("Network", e.toString());
        }
        return null;
    }
}
