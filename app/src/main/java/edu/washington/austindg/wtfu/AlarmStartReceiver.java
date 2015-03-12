package edu.washington.austindg.wtfu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmStartReceiver extends BroadcastReceiver {

    public static final String TAG = AlarmStartReceiver.class.getSimpleName();

    public AlarmStartReceiver() {  }

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean snoozeAlarm = intent.getBooleanExtra("snooze", false);
        Alarm alarm = (Alarm) intent.getSerializableExtra("alarm");

        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (alarm.getDays()[dayOfWeek] && alarm.getEnabled()) {

            if(!snoozeAlarm) {
                repeatAlarmTomorrow(alarm, context);
            }

            Intent wakePhoneIntent = new Intent(context, WakeTheFuckUpActivity.class);
            wakePhoneIntent.putExtra("alarm", alarm);
            wakePhoneIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(wakePhoneIntent);
        }
    }

    // start alarm again exactly one day from now
    public void repeatAlarmTomorrow(Alarm alarm, Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent repeatAlarmIntent = AlarmScheduler.createPendingIntent(alarm, false);
        long oneDayFromNowMs = System.currentTimeMillis() + AlarmManager.INTERVAL_DAY;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, oneDayFromNowMs, repeatAlarmIntent);
    }
}
