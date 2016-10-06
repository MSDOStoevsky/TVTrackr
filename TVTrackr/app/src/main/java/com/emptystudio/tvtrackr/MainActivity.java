package com.emptystudio.tvtrackr;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Show> search = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccessDatabase db = new AccessDatabase(this);

        /* database entry tests */
        ArrayList<String> generes = new ArrayList<>();
        generes.add(0, "Comedy");

        db.addFavorite(new Show("Hey Arnold", generes, "", ""));

        List<Show> favs = db.getFavorites();
        Log.d("Returned", favs.toString());

        if(!isNetworkAvailable()){
            Toast.makeText(MainActivity.this, "Could not connect to the internet!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

    private void search(JSONArray jayson) {
        if (jayson != null && jayson.length() != 0) {
            for (int i = 0; i < jayson.length(); i++) {
                try {
                    JSONObject ob = jayson.getJSONObject(i);
                    JSONObject show = ob.getJSONObject("show");
                    ArrayList<String> genres = new ArrayList<String>(Arrays.asList(show.getString("show").split("\\s*,\\s*")));
                    Show current = new Show(show.getString("name"), genres, show.getString("schedule"), show.getString("image"));
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "That's not supposed to happen.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /*
        This function is a test that gets called by the button onclick
        event, and will probably be thrown out soon so that the search bar
        can make a call to the AccessWebsite task.
     */
    public void displayJSON(View v){
        new AccessWebsite(getBaseContext(), findViewById(R.id.root)).execute("cory+in+the+house");//calls the doInBackground
    }
}