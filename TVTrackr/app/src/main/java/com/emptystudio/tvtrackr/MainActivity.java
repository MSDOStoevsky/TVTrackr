package com.emptystudio.tvtrackr;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        AccessDatabase db = new AccessDatabase(this);

        //db.addFavorite(new Show("Hey Arnold", new ));
        
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

    /*
        This function is a test that gets called by the button onclick
        event, and will probably be thrown out soon so that the search bar
        can make a call to the AccessWebsite task.
     */
    public void displayJSON(View v){
        new AccessWebsite(getBaseContext(), findViewById(R.id.root)).execute("cory+in+the+house");//calls the doInBackground
    }
}
