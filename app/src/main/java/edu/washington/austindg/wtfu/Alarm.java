package edu.washington.austindg.wtfu;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by austindg on 2/27/15.
 */
public class Alarm implements Serializable {

    private static final String TAG = "Alarm";
    private static final String ALARM_KEY = "alarms";

    private int startTime;
    private boolean[] days;
    private boolean enabled;

    public Alarm() {
        startTime = 0;
        days = new boolean[7];
        enabled = false;
    }

    public void setStartTime(int time) {
        this.startTime = time;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public boolean[] setDays() {
        return this.days;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public static void serialize(Context context, List<Alarm> alarms) throws IOException {

        // serialize alarm to json
        Gson gson = new Gson();
        String data = gson.toJson(alarms);

        // put data into shared preferences
        SharedPreferences pref = context.getSharedPreferences(ALARM_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(ALARM_KEY, data);
        editor.apply();

        Log.i(TAG, data);
    }

    public static List<Alarm> unserialize(Context context) {
        // get shared preferences
        // defaulting to empty json array
        SharedPreferences prefs = context.getSharedPreferences(ALARM_KEY, Context.MODE_PRIVATE);
        String data = prefs.getString(ALARM_KEY, "[]");

        // unserialize json string into List<Alarm>
        Gson gson = new Gson();
        return gson.fromJson(data, new TypeToken<List<Alarm>>(){}.getType());
    }
}
