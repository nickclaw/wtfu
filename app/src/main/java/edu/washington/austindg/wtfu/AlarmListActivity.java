package edu.washington.austindg.wtfu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AlarmListActivity extends ActionBarActivity {

    public static final String TAG = "AlarmListActivity";
    private AlarmAdapter alarmAdapter;
    private AlarmRepository alarmRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        alarmRepository = App.getAlarmRepository();
    }

    @Override
    public void onResume() {
        super.onResume();

        // build list view
        ListView alarmView = (ListView) findViewById(R.id.alarmList);
        alarmAdapter = new AlarmAdapter(this, alarmRepository.deserialize());
        alarmView.setAdapter(alarmAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            alarmRepository.serialize();
        } catch (Exception e) {
            Log.i(TAG, "Error serializing alarmList: " + e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings:
                Log.i("menu", "Settings selected.");
                RevengeManager.getInstance().revenge();
                return true;
            case R.id.action_search:
                // create new alarm
                Alarm alarm = new Alarm();
                alarmRepository.addAlarm(alarm);

                // data change, refresh the view please
                alarmAdapter.notifyDataSetChanged();

                // randomly sayInsult user
                Insulter.getInstance().insult();
                break;
            case R.id.action_clear:
                // clear all alarms
                // TODO stop pending intents
                alarmRepository.clearAlarms();
                alarmAdapter.notifyDataSetChanged();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
