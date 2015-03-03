package edu.washington.austindg.wtfu;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
    private static final String ALARM_KEY = "alarms1";
    public static final String AM = "AM";
    public static final String PM = "PM";

    private int startTime;
    private String amPm;
    private boolean[] days;
    private boolean enabled;

    public Alarm() {
        this(420, PM, new boolean[7], false);
    }

    public Alarm(int startTime, String amPm, boolean[] days, boolean enabled) {
        this.startTime = startTime;
        this.amPm = amPm;
        this.days = days;
        this.enabled = enabled;
    }

    public void setStartTime(int time) {
        handler.firePropertyChange("startTime", this.startTime, time);
        this.startTime = time;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }

    public String getAmPm() {
        return this.amPm;
    }

    public void setDays(boolean[] days) {
        handler.firePropertyChange("days", this.days, days);
        this.days = days;
    }

    public boolean[] getDays() {
        return this.days;
    }

    public void setEnabled(boolean enabled) {
        handler.firePropertyChange("enabled", this.enabled, enabled);
        this.enabled = enabled;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    //
    // Listenable implementation
    //

    private transient final PropertyChangeSupport handler = new PropertyChangeSupport(this);
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        handler.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        handler.removePropertyChangeListener(listener);
    }

    //
    // Serialization implementation
    //

    /**
     * Serialize a list of alarms to a contexts SharedPreferences
     * @param context
     * @param alarms
     * @throws IOException
     */
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

    /**
     * Deserialize a list of alarms from a contexts SharedPreferences
     * @param context
     * @return
     */
    public static List<Alarm> deserialize(Context context) {
        // get shared preferences
        // defaulting to empty json array
        SharedPreferences prefs = context.getSharedPreferences(ALARM_KEY, Context.MODE_PRIVATE);
        String data = prefs.getString(ALARM_KEY, "[]");

        // deserialize json string into List<Alarm>
        Gson gson = new Gson();
        return gson.fromJson(data, new TypeToken<List<Alarm>>(){}.getType());
    }
}
