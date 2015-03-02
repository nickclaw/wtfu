package edu.washington.austindg.wtfu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AlarmListActivity extends ActionBarActivity {

    public static final String TAG = "AlarmListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
    }

    @Override
    public void onResume() {
        super.onResume();

        final List<Alarm> alarms = new ArrayList<Alarm>() {{
            add(new Alarm(330, Alarm.AM, new boolean[] {true, false, false, true, true, false, true}, true));
            add(new Alarm(340, Alarm.PM, new boolean[] {true, true, true, true, true, true, true}, true));
            add(new Alarm(350, Alarm.PM, new boolean[] {true, true, false, true, true, false, true}, false));
        }};

        final AlarmAdapter alarmAdapter = new AlarmAdapter(this, alarms);

        ListView alarmList = (ListView) findViewById(R.id.alarmList);
        alarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Alarm Item Clicked");
                Alarm clickedAlarm = alarmAdapter.getItem(position);
                EditAlarmFragment editAlarmFragment = new EditAlarmFragment();
                editAlarmFragment.setAlarm(clickedAlarm);
                getFragmentManager().beginTransaction()
                        .add(R.id.container, editAlarmFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

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
