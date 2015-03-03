package edu.washington.austindg.wtfu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class AlarmListActivity extends ActionBarActivity
    implements PropertyChangeListener {

    public static final String TAG = "AlarmListActivity";
    public static final List<Alarm> alarmList = new ArrayList<Alarm>();
    private AlarmAdapter alarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            Alarm.serialize(this, alarmList);
        } catch (Exception e) {
            Log.i(TAG, "Error serializing alarmList: " + e.getMessage());
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        // replace alarms
        List<Alarm> list = Alarm.deserialize(this);
        alarmList.clear();
        for (Alarm alarm : list) {
            alarm.addPropertyChangeListener(this);
            alarmList.add(alarm);
        }

        // build list view
        ListView alarmView = (ListView) findViewById(R.id.alarmList);
        alarmAdapter = new AlarmAdapter(this, alarmList);
        alarmView.setAdapter(alarmAdapter);
    }

    public void propertyChange(PropertyChangeEvent event) {
        Log.i(TAG, "Property '" + event.getPropertyName() + "' has changed: " + event.getNewValue().toString());
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

        if (id == R.id.action_settings) {
            // Start evil Preferences Activity
            return true;
        } else if(id == R.id.action_search) {

            // create new alarm
            Alarm alarm = new Alarm();
            alarm.addPropertyChangeListener(this);
            alarmList.add(alarm);

            // data change, refresh the view please
            alarmAdapter.notifyDataSetChanged();

            // randomly sayInsult user
            Insulter.getInstance().insult();
        }

        return super.onOptionsItemSelected(item);
    }
}
