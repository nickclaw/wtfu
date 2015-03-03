package edu.washington.austindg.wtfu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AlarmListActivity extends ActionBarActivity
    implements PropertyChangeListener {

    public static final String TAG = "AlarmListActivity";
    public static final List<Alarm> alarmList = new ArrayList<Alarm>();

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
        for(Alarm alarm : list) {
            alarm.addPropertyChangeListener(this);
            alarmList.add(alarm);
        }

        // build list view
        ListView alarmView = (ListView) findViewById(R.id.alarmView);
        AlarmAdapter alarmAdapter = new AlarmAdapter(this, alarmList);
        alarmView.setAdapter(alarmAdapter);
    }

    public void propertyChange(PropertyChangeEvent event) {
        Log.i(TAG, "Property '" + event.getPropertyName() + "' changed: " + event.getNewValue().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
