package edu.washington.austindg.wtfu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;

public class StartAlarmReceiver extends BroadcastReceiver {

    public static final String TAG = "StartAlarmReceiver";


    public StartAlarmReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Alarm alarm = (Alarm) intent.getSerializableExtra("alarm");
        Log.i(TAG, "IN receiver: " + Arrays.toString(alarm.getDays()));

        Calendar cal = Calendar.getInstance();
        // sunday -> 0, monday -> 1, and so on
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(alarm.getDays()[dayOfWeek] && alarm.getEnabled()) {
            Log.i(TAG, "ALARM ALARM ALARM I HATE YOU");
            Insulter insulter = Insulter.getInstance();
            insulter.insult();
        } else {
            Log.i(TAG, "Wrong day of week or wasn't enabled");
        }

    }
}
