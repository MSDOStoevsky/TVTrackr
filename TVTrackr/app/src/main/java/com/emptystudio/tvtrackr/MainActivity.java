package com.emptystudio.tvtrackr;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
    private TabLayout tabLayout;
    private AccessDatabase db;
    private ArrayList<Show> search = new ArrayList<>();
    private int[] tabIcons = {
            R.drawable.ic_action_home,
            R.drawable.ic_action_favorite,
            R.drawable.ic_action_schedule
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new AccessDatabase(this);

        //search_button = (Button) findViewById(R.id.search_button);
        //text_box = (EditText) findViewById(R.id.text_box);

        /*search_button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        displayShows(text_box.getText().toString());
                    }
                }
        );*/
        /*List blah = new ArrayList<String>();
        blah.add("Educational");
        Show something = new Show("Video Game High School", blah, "idk", "idk");
        db.addFavorite(something);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager, db.getFavorites());

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        if (!isNetworkAvailable()) {
            Toast.makeText(MainActivity.this, "There was an error trying to connect to the internet!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*private void initFavTab(List fav_shows){**
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.fav_recycler);
        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new DataAdapter(fav_shows);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    //Toast.makeText(getApplicationContext(), countries.get(position), Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }*/

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager, List<Show> fav_db) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FirstTab());
        //init db info
        SecondTab tab = new SecondTab();
        tab.setDataList(fav_db);
        adapter.addFragment(tab);
        adapter.addFragment(new ThirdTab());
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        //private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        /*public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }*/

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
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