package com.emptystudio.tvtrackr;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dylan on 9/21/2016.
 */
public class AccessWebsite extends AsyncTask<String, Void, String>{//third param should be JSONObject

    private Context context; //this allows this thread to update the UI thread
    private View view; //this allows this thread to update the View Elements

    public AccessWebsite (Context context, View root){
        this.context = context;
        this.view = root;
    }

    @Override
    protected String doInBackground(String... params) {
        String ret = null;//should be JSONObject
        try {
            URL url = new URL("http://api.tvmaze.com/search/shows?q=" + params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line = "";
                while((line = bufferedReader.readLine()) != null)
                    ret += line;
                bufferedReader.close();
            } finally {
                urlConnection.disconnect();
            }
        } catch(Exception e) {
            Log.e("Network", e.toString());
        }
        return ret;//should be JSONObject
    }

    protected void onPostExecute(String result) {
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(result);
    }
}
