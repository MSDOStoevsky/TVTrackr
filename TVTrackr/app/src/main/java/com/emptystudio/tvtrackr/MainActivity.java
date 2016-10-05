package com.emptystudio.tvtrackr;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        AccessDatabase db = new AccessDatabase(this);

        /* database entry tests */
        ArrayList<String> generes = new ArrayList<>();
        generes.add(0, "Comedy");
        //Show heyarnold = ;
        //Log.d("CREATED?", heyarnold.toString());
        db.addFavorite(new Show("Hey Arnold", generes, "something", "url"));
        Log.d("WORKED?", Integer.toString(db.numberOfRows()));
        List<Show> favs = db.getFavorites();
        Log.d("Returned", favs.toString());
        db.clearFavorites();
        Log.d("WORKED?", Integer.toString(db.numberOfRows()));

        db.close();

        if(!isNetworkAvailable()){
            Toast.makeText(MainActivity.this, "There was an error trying to connect to the internet!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
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
