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
        AlarmManager man = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        List<Show> favs = db.getAllFavorites();
        for (Show s : favs) {
            String[] days = s.getSchedule().split(", ");
            String time = s.getAirTime();

            for (int i = 0; i < days.length; i++) {
                SimpleDateFormat t = new SimpleDateFormat("K:mm E");
                String str = time + days[i];
                try {
                    Date da = t.parse(str);
                    cal.setTime(da);
                    Intent intent = new Intent(this, PushNotification.class);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.camera).setContentTitle("Your show is on!").setContentText(s.getName());
                    intent.putExtra("not", mBuilder.build());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pend = PendingIntent.getBroadcast(this, 0, intent, 0);
                    man.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pend);
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
