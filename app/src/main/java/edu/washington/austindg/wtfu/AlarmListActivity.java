package edu.washington.austindg.wtfu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AlarmListActivity extends ActionBarActivity {

    public static final String TAG = "AlarmListActivity";
    private AlarmAdapter alarmAdapter;
    private List<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
    }

    @Override
    public void onResume() {
        super.onResume();

        alarms = new ArrayList<Alarm>() {{
            add(new Alarm(330, Alarm.AM, new boolean[] {true, false, false, true, true, false, true}, true));
            add(new Alarm(340, Alarm.PM, new boolean[] {true, true, true, true, true, true, true}, false));
            add(new Alarm(310, Alarm.PM, new boolean[] {false, false, false, false, false, false, false}, true));
        }};

        alarmAdapter = new AlarmAdapter(this, alarms);
        ListView alarmList = (ListView) findViewById(R.id.alarmList);
        alarmList.setAdapter(alarmAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
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
            alarms.add(new Alarm());
            // data change, refresh the view please
            alarmAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }
}
