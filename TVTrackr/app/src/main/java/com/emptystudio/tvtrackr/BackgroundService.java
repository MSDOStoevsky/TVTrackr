package com.emptystudio.tvtrackr;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BackgroundService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        AccessDatabase db = new AccessDatabase(this);
        AlarmManager man = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();

        List<Show> favs = db.getAllFavorites();
        for (Show s : favs) {
            s.getSchedule();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public BackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
