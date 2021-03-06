package edu.washington.austindg.wtfu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.washington.austindg.wtfu.wakeup.AdventureActivity;
import edu.washington.austindg.wtfu.wakeup.ImALittleFatGirlActivity;

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
        WakeupManager.getInstance().restore(this);
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

        // randomly sayInsult user
        Insulter.getInstance().insult();

        switch(id) {
            case R.id.action_settings:
                Log.i("menu", "Settings selected.");
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_search:
                // create new alarm
                Alarm alarm = new Alarm();
                alarmRepository.addAlarm(alarm);

                // data change, refresh the view please
                alarmAdapter.notifyDataSetChanged();
                break;
            case R.id.action_clear:
                // clear all alarms
                List<Alarm> alarms = alarmRepository.getAlarms();
                App.getAlarmScheduler().stopAlarms(alarms);
                alarmRepository.clearAlarms();
                alarmAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_alarm:
                Intent wakePhoneIntent = new Intent(this, WakeTheFuckUpActivity.class);
                WakeupManager.getInstance().put(WakeTheFuckUpActivity.class);
                wakePhoneIntent.putExtra("alarm", new Alarm());
                wakePhoneIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(wakePhoneIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
