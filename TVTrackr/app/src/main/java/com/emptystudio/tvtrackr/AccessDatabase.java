package com.emptystudio.tvtrackr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dylan on 9/24/2016.
 */
public class AccessDatabase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "FAVORITES";

    // Table Name
    private static final String TABLE_FAVORITES = "user_favorites";
    // Columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_GENRES = "genres";
    private static final String KEY_SCHEDULE = "schedule";
    private static final String KEY_AIRTIME = "airtime";
    private static final String KEY_STATUS = "status";
    private static final String KEY_IMAGE = "image";
    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_GENRES, KEY_SCHEDULE, KEY_AIRTIME, KEY_STATUS, KEY_IMAGE};

    public AccessDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // SQL statement to create book table
        String CREATE_TABLE = "CREATE TABLE "+TABLE_FAVORITES+" ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "description TEXT, "+
                "genres TEXT, "+
                "schedule TEXT, "+
                "airtime TEXT, "+
                "status TEXT, "+
                "image TEXT);";

        // create favorites table
        db.execSQL(CREATE_TABLE);
        Log.d("onCreate", "CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(AccessDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_FAVORITES);
        return numRows;
    }

    public boolean addFavorite(Show show){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, show.getName());
        values.put(KEY_DESCRIPTION, show.getDescription());
        values.put(KEY_GENRES, show.getGenres());
        values.put(KEY_SCHEDULE, show.getSchedule());
        values.put(KEY_AIRTIME, show.getAirTime());
        values.put(KEY_STATUS, show.getStatus());
        values.put(KEY_IMAGE, show.getImageURL());
        // insert
        db.insert(TABLE_FAVORITES, null, values);

        db.close();

        return true;
    }

    public boolean removeFavorite(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES,
                "id = ? ",
                new String[]{Integer.toString(id)});
        db.close();

        return true;
    }

    public boolean clearFavorites()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FAVORITES);
        db.close();

        return true;
    }

    public Show getFavorite(Show show) {
        Show retShow = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_FAVORITES + " WHERE " + KEY_NAME + " = ?";
        Cursor c = db.rawQuery(query, new String[]{show.getName()});

        if (c.moveToFirst()) {
            retShow = new Show();
            do {
                retShow.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                retShow.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                retShow.setGenres(Arrays.asList(c.getString(c.getColumnIndex(KEY_GENRES))));
                retShow.setSchedule(Arrays.asList(c.getString(c.getColumnIndex(KEY_SCHEDULE))));
                retShow.setAirTime(c.getString(c.getColumnIndex(KEY_AIRTIME)));
                show.setImageURL(c.getString(c.getColumnIndex(KEY_IMAGE)));
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return retShow;
    }

    public List<Show> getAllFavorites(){
        List<Show> favs = new ArrayList<Show>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_FAVORITES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            Show show;

            do {
                show = new Show();
                show.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                show.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                show.setGenres(Arrays.asList(cursor.getString(cursor.getColumnIndex(KEY_GENRES))));
                show.setSchedule(Arrays.asList(cursor.getString(cursor.getColumnIndex(KEY_SCHEDULE))));
                show.setAirTime(cursor.getString(cursor.getColumnIndex(KEY_AIRTIME)));
                show.setImageURL(cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));

                favs.add(show);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        return favs;
    }
}
