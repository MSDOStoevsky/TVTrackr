package com.emptystudio.tvtrackr;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Calendar;

public class BackgroundService extends Service {

    public BackgroundService() {
        AccessDatabase db = new AccessDatabase(this);
        AlarmManager man = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        ArrayList<Show> favs = (ArrayList) db.getFavorites();
        for (Show s : favs) {
            s.getSchedule();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
