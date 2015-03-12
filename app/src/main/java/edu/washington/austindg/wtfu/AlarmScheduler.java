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
import java.util.List;
import java.util.TimeZone;

public class AlarmScheduler extends BroadcastReceiver {

    public static String TAG = AlarmScheduler.class.getSimpleName();
    public static final int STARTING_SNOOZE_ID = 2000;
    private static AlarmScheduler alarmScheduler = new AlarmScheduler();

    public AlarmScheduler() {  }

    public static AlarmScheduler getInstance() {
        return alarmScheduler;
    }

    public void startAlarm(Alarm alarm) {
        AlarmManager manager = (AlarmManager) App.getContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingAlarm = createPendingIntent(alarm, false);

        // set alarm timing schedule
        Calendar cal = Calendar.getInstance();
        String timeZoneId = TimeZone.getDefault().getID();

        Time alarmTime = new Time(timeZoneId);
        alarmTime.set(00, alarm.getStartMinutes(), alarm.getStartHours(),
                cal.get(Calendar.DAY_OF_MONTH) ,cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));

        Time nowTime = new Time(timeZoneId);
        nowTime.setToNow();

        long nowTimeMs = nowTime.toMillis(true);
        long alarmTimeMs = alarmTime.toMillis(true);

        // could alarm go off today?
        if(alarmTime.after(nowTime)) {
            Log.i(TAG, "Alarm will sound in: " + String.valueOf((alarmTimeMs - nowTimeMs) / 1000) + " seconds");
            manager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeMs, pendingAlarm);
        } else { // passed, wait until next week
            Log.i(TAG, "Alarm won't sound today, but next week");
            manager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeMs + AlarmManager.INTERVAL_DAY, pendingAlarm);
        }
    }

    public void startSnoozeForAlarm(Alarm alarm, int delayMinutes) {
        AlarmManager manager = (AlarmManager) App.getContext().getSystemService(Context.ALARM_SERVICE);
        boolean[] days = new boolean[7];
        Arrays.fill(days, true);
        Alarm snoozeAlarm = new Alarm();
        snoozeAlarm.setDays(days);
        snoozeAlarm.setEnabled(true);
        snoozeAlarm.setId(alarm.getId() + STARTING_SNOOZE_ID);

        long snoozeTime = minutesToMilliseconds(delayMinutes);
        PendingIntent pendingAlarm = createPendingIntent(alarm, true);
        manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + snoozeTime, pendingAlarm);
        Log.i(TAG, "Snooze Alarm will start in " + delayMinutes + " min");
    }

    public void startAlarms(List<Alarm> alarms) {
        for(Alarm alarm: alarms) {
            startAlarm(alarm);
        }
    }

    public void stopAlarm(Alarm alarm) {
        AlarmManager manager = (AlarmManager) App.getContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmToCancel = createPendingIntent(alarm, false);
        manager.cancel(alarmToCancel);
        alarmToCancel.cancel();
    }

    public void stopAlarms(List<Alarm> alarms) {
        for(Alarm alarm: alarms) {
            stopAlarm(alarm);
        }
    }

    public static PendingIntent createPendingIntent(Alarm alarm, boolean snoozeAlarm) {
        Intent alarmReceiverIntent = new Intent(App.getContext(), AlarmStartReceiver.class);
        alarmReceiverIntent.putExtra("alarm", alarm);
        if(snoozeAlarm) {
            alarmReceiverIntent.putExtra("snooze", true);
        }
        PendingIntent pendingAlarm = PendingIntent.getBroadcast(App.getContext(), alarm.getId(),
                alarmReceiverIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingAlarm;
    }

    public static String amPmFromHours(int hours) {
        if(hours > 11 && hours <= 23) { // is pm
            return Alarm.PM;
        } else { // is am
            return Alarm.AM;
        }
    }

    public static long minutesToMilliseconds(int minutes) {
        return minutes * 60 * 1000;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Device Boot: Start alarms again");
        List<Alarm> alarmList = App.getAlarmRepository().deserialize();
        startAlarms(alarmList);
    }
}
