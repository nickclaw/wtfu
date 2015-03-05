package edu.washington.austindg.wtfu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class StartAlarmReceiver extends BroadcastReceiver {

    public StartAlarmReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Alarm alarm = (Alarm) intent.getSerializableExtra("alarm");

        Calendar calendar = Calendar.getInstance();
        // sunday -> 0, monday -> 1, and so on
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(alarm.getDays()[dayOfWeek]) {

        }

    }
}
