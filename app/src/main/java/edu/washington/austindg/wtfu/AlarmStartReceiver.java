package edu.washington.austindg.wtfu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmStartReceiver extends BroadcastReceiver {

    public static final String TAG = AlarmStartReceiver.class.getSimpleName();

    public AlarmStartReceiver() {  }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Alarm alarm = (Alarm) intent.getSerializableExtra("alarm");
            Log.i(TAG, alarm.toString());

            Calendar cal = Calendar.getInstance();
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (alarm.getDays()[dayOfWeek] && alarm.getEnabled()) {
                Log.i(TAG, "ALARM ALARM ALARM I HATE YOU");

                // start alarm again exactly one day from now
                PendingIntent repeatAlarmIntent = AlarmScheduler.createPendingIntent(alarm);
                long oneDayFromNowMs = System.currentTimeMillis() + AlarmManager.INTERVAL_DAY;
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, oneDayFromNowMs, repeatAlarmIntent);

                // snooze or stop activities start.. insult ppl

                Insulter insulter = Insulter.getInstance();
                insulter.insult();
            } else {
                Log.i(TAG, "Wrong day of week or wasn't enabled");
            }
        }
    }
}
