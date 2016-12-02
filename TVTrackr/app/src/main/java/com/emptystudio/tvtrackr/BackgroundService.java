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

        AccessDatabase db = new AccessDatabase(this);
        Intent intent = new Intent(this, PushNotification.class);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.camera).setContentTitle("My notification").setContentText("Hello World!");

        intent.putExtra("not", mBuilder.build());

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);


        AlarmManager man = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pend = PendingIntent.getBroadcast(this, 0, intent, 0);
        Calendar cal = Calendar.getInstance();
        /*List<Show> favs = db.getAllFavorites();
        for (Show s : favs) {
            try {
                JSONObject temp = new JSONObject(s.getSchedule());
                String time = temp.getString("time");
                JSONArray days = temp.getJSONArray("days");
                for (int i = 0; i < days.length(); i++) {
                    SimpleDateFormat t = new SimpleDateFormat("K:mm E");
                    String str = time + days.getString(i);
                    try {
                        Date da = t.parse(str);
                        cal.setTime(da);
                    } catch (ParseException e ) {

                    }
                    man.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pend);
                }
            } catch (JSONException e) {
                Toast.makeText(BackgroundService.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }*/
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 40);
        cal.set(Calendar.SECOND, 10);
        man.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pend);
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
