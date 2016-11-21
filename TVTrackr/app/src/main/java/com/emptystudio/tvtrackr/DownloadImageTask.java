package com.emptystudio.tvtrackr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Collin on 11/19/2016.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    protected Bitmap doInBackground(String... urls) {
        Bitmap bmp = null;

        try {
            InputStream in = new URL(urls[0]).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            Log.e("DownloadImageTask", "MalformedURLException");
        } catch (IOException e) {
            Log.e("DownloadImageTask", "IOException");
        }

        return bmp;
    }

    protected void onPostExecute(Bitmap result) {
    }
}
