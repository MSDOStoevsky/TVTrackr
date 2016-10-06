package com.emptystudio.tvtrackr;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText text_box;
    private Button search_button;
    private AccessDatabase db;
    private ArrayList<Show> search = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new AccessDatabase(this);

        search_button = (Button)findViewById(R.id.search_button);
        text_box   = (EditText)findViewById(R.id.text_box);

        search_button.setOnClickListener(
            new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    displayShows(text_box.getText().toString());
                }
            }
        );
        /* database entry tests *
        ArrayList<String> generes = new ArrayList<>();
        generes.add(0, "Comedy");
        db.addFavorite(new Show("Hey Arnold", generes, "something", "url"));
        /**/
        if(!isNetworkAvailable()){
            Toast.makeText(MainActivity.this, "There was an error trying to connect to the internet!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    //------ button functions ------//

    public void displayShows(String showname){
        new AccessWebsite(getBaseContext(), findViewById(R.id.root)).execute(showname);//calls the doInBackground
    }

    public void displayFavorites(View v){

        List<Show> favs = db.getFavorites();
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(favs.toString());
    }


    //------ augmenters ------//

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
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
                    ArrayList<String> genres = new ArrayList<String>(Arrays.asList(show.getString("show").split("\\s*,\\s*")));
                    Show current = new Show(show.getString("name"), genres, show.getString("schedule"), show.getString("image"));
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "That's not supposed to happen.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}