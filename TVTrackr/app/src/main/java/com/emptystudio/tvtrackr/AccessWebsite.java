package com.emptystudio.tvtrackr;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by Dylan on 9/21/2016.
 *
 * <String, Void, JSONArray>
 * @param1 Object that doInBackground accepts
 * @param2 No clue... maybe the progress fn returns this?
 * @param3 Object that doInBackground returns
 */
public class AccessWebsite extends AsyncTask<String, Void, JSONArray>{

    private Context context; //this allows this thread to update the UI thread
    private View view; //this allows this thread to update the View Elements

    public AccessWebsite (Context context, View root){
        this.context = context;
        this.view = root;
    }

    @Override
    protected JSONArray doInBackground(String... params) {

        JSONArray ret = null;

        try{
            InetAddress.getByName("api.tvmaze.com").isReachable(3);
        }catch (UnknownHostException e){
            Log.e("AccessWebsite", "UnknownHostException");
        } catch (IOException e){
            Log.e("AccessWebsite", "IOException");
        }
        finally{
            try {
                URL url = new URL("http://api.tvmaze.com/search/shows?q=" + params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    for (String line = null; (line = bufferedReader.readLine()) != null;) {
                        builder.append(line).append("\n");
                    }
                    JSONTokener tokener = new JSONTokener(builder.toString());
                    ret = new JSONArray(tokener);
                    bufferedReader.close();
                } finally {
                    urlConnection.disconnect();
                }
            } catch(Exception e) {
                Log.e("AccessWebsite", e.toString());
            }
        }
        return ret;
    }

    /*
        This is the function that is automatically called after doInBackground()
        has finished executing. TThe implementation locates the TextView Object on the View,
        and sets the text to the stringified JSON.

        Using the built in JSON libraries will allow the JSONArray to be
        broken up to display the proper information.
     */
    protected void onPostExecute(JSONArray result) {

        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(result.toString());
    }
}