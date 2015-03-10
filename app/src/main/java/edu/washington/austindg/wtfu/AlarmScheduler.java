package edu.washington.austindg.wtfu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

public class AlarmScheduler extends BroadcastReceiver {

    public static String TAG = AlarmScheduler.class.getSimpleName();
    private static AlarmScheduler alarmScheduler = new AlarmScheduler();

    public AlarmScheduler() {  }

    public static AlarmScheduler getInstance() {
        return alarmScheduler;
    }

    public void startAlarm(Alarm alarm) {

        AlarmManager manager = (AlarmManager) App.getContext().getSystemService(Context.ALARM_SERVICE);

        // set alarm timing schedule
        Calendar cal = Calendar.getInstance();
        String timeZoneId = TimeZone.getDefault().getID();

        Time alarmTime = new Time(timeZoneId);
        int alarmStartMinutes = alarm.getStartMinutes();
        int alarmStartHours = alarm.getStartHours();
        alarmTime.set(cal.get(Calendar.SECOND), alarmStartMinutes, alarmStartHours,
                cal.get(Calendar.DAY_OF_MONTH) ,cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
        alarmTime.normalize(false);

        Time nowTime = new Time(timeZoneId);
        nowTime.setToNow();
        nowTime.normalize(false);

        long nowTimeMs = nowTime.toMillis(true);
        long alarmTimeMs = alarmTime.toMillis(true) - 40000; // ~avg delay in ms
        Log.i(TAG, "Now ms: " + String.valueOf(nowTimeMs));
        Log.i(TAG, "Alarm ms: " + String.valueOf(alarmTimeMs));
        Log.i(TAG, "Will sound in (Alarm - Now) ms: " + String.valueOf(alarmTimeMs - nowTimeMs));
        Log.i(TAG, "Before receiver: " + Arrays.toString(alarm.getDays()));

        // could alarm go off today?
        if(alarmTime.after(nowTime)) {
            Log.i(TAG, "Will sound today");
            //manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTimeMs, AlarmManager.INTERVAL_DAY, pendingAlarm);
        } else { // passed, wait until next week
            Log.i(TAG, "Won't sound today");
            //manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTimeMs + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, pendingAlarm);
        }
    }

    public PendingIntent createPendingIntent(Alarm alarm) {
        Intent alarmIntervalIntent = new Intent(App.getContext(), AlarmReceiver.class);
        alarmIntervalIntent.putExtra("alarm", alarm);
        PendingIntent pendingAlarm = PendingIntent.getBroadcast(App.getContext(), alarm.getId(),
                alarmIntervalIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingAlarm;
    }

    public void stopAlarm(Alarm alarm) {
        AlarmManager manager = (AlarmManager) App.getContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmToCancel = createPendingIntent(alarm);
        manager.cancel(alarmToCancel);
        alarmToCancel.cancel();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
