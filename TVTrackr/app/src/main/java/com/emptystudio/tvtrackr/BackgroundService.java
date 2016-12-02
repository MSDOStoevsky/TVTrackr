/**
 * Elijah Pass
 * 2016
 * This is the service that runs in the background. Handles creation of notifications from the favorites.
 */

package com.emptystudio.tvtrackr;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class BackgroundService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        AccessDatabase db = new AccessDatabase(this); // Opens a gateway to the database
        AlarmManager man = (AlarmManager) getSystemService(ALARM_SERVICE); // Acecss the alarm system in android
        Calendar cal = Calendar.getInstance(); // Current condition of the calendar
        List<Show> favs = db.getAllFavorites(); // Gets the favorites from the database
        for (Show s : favs) {
            String[] days = s.getSchedule().split(", "); // There could be multiple days that the show is on
            String time = s.getAirTime(); // Gets the time that the show is on

            for (int i = 0; i < days.length; i++) {
                SimpleDateFormat t = new SimpleDateFormat("K:mm E"); // Easy way of setting up the date and time
                String str = time + days[i];
                try {
                    Date da = t.parse(str);
                    cal.setTime(da); // Sets the current time to the time obtained
                    Intent intent = new Intent(this, PushNotification.class);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Your show is on!").setContentText(s.getName()); // Creates the notification
                    intent.putExtra("not", mBuilder.build()); // Puts the notification in to the intent
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP); // These are set so that the intent doesn't lose its extras
                    PendingIntent pend = PendingIntent.getBroadcast(this, 0, intent, 0);
                    man.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pend); // Tells android when to create the notification
                } catch (ParseException e) {

                }
            }
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
