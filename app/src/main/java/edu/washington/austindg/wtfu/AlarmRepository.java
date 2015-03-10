package edu.washington.austindg.wtfu;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

/**
 * Created by austindg on 3/9/15.
 */
public class AlarmRepository {

    public static final String TAG = AlarmRepository.class.getSimpleName();
    private static AlarmRepository instance = new AlarmRepository();
    private List<Alarm> alarms;

    private AlarmRepository() {  }

    public static AlarmRepository getInstance() { return instance; }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    public List<Alarm> getAlarms() {
        return this.alarms;
    }

    public void addAlarm(Alarm alarm) {
        this.alarms.add(alarm);
    }

    /**
     * Serialize this repo's alarms to a contexts SharedPreferences
     * @throws java.io.IOException
     */
    public void serialize() throws IOException {

        Context context = App.getContext();

        // serialize alarm to json
        Gson gson = new Gson();
        String data = gson.toJson(this.alarms);

        // put data into shared preferences
        SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.alarm_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(context.getString(R.string.alarm_key), data);
        editor.apply();
    }

    /**
     * Deserialize this repo's alarms from a contexts SharedPreferences
     * @return the deserialized alarms
     */
    public List<Alarm> deserialize() {
        // get shared preferences
        // defaulting to empty json array
        Context context = App.getContext();
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.alarm_key), Context.MODE_PRIVATE);
        String data = prefs.getString(context.getString(R.string.alarm_key), "[]");

        // deserialize json string into List<Alarm>
        Gson gson = new Gson();
        this.alarms = gson.fromJson(data, new TypeToken<List<Alarm>>(){}.getType());
        return this.alarms;
    }

}
