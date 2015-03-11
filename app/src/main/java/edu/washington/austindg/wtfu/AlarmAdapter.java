package edu.washington.austindg.wtfu;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wtfu group
 */
public class AlarmAdapter extends ArrayAdapter<Alarm> {

    private static final String TAG = "AlarmAdapter";
    private static final int STARTING_POSITION_NUM = 1;
    private static AlarmScheduler alarmScheduler;
    private final Activity activity;
    private final List<Alarm> alarmList;

    public AlarmAdapter(Activity activity, List<Alarm> alarmList) {
        super(activity, R.layout.alarm_item_view, alarmList);
        this.activity = activity;
        this.alarmList = alarmList;
        this.alarmScheduler = App.getAlarmScheduler();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.alarm_item_view, parent, false);

        final Alarm alarm = alarmList.get(position);
        alarm.setId(position + STARTING_POSITION_NUM);
        boolean[] daysEnabled = alarm.getDays();

        // Views: rowView
        final TextView timeView = (TextView) rowView.findViewById(R.id.time);
        final TextView amPmView = (TextView) rowView.findViewById(R.id.am_pm);
        final TextView daysOfWeekView = (TextView) rowView.findViewById(R.id.days_of_week);
        final ImageButton editAlarmBtn = (ImageButton) rowView.findViewById(R.id.edit_alarm_btn);
        final ImageButton deleteBtn = (ImageButton) rowView.findViewById(R.id.delete_alarm);
        final Switch enabledSwitch = (Switch) rowView.findViewById(R.id.enabled_switch);
        // Views: editAlarmView
        final TimePicker timePicker = (TimePicker) rowView.findViewById(R.id.time_picker);
        final CheckBox cbSunday = (CheckBox) rowView.findViewById(R.id.cb_sun);
        final CheckBox cbMonday = (CheckBox) rowView.findViewById(R.id.cb_mon);
        final CheckBox cbTuesday = (CheckBox) rowView.findViewById(R.id.cb_tue);
        final CheckBox cbWednesday = (CheckBox) rowView.findViewById(R.id.cb_wed);
        final CheckBox cbThursday = (CheckBox) rowView.findViewById(R.id.cb_thu);
        final CheckBox cbFriday = (CheckBox) rowView.findViewById(R.id.cb_fri);
        final CheckBox cbSaturday = (CheckBox) rowView.findViewById(R.id.cb_sat);

        // Set Views: rowView
        timeView.setText(makeDisplayHours(alarm.getStartHours()) + ":" + makeDisplayMinutes(alarm.getStartMinutes()));
        amPmView.setText(alarm.getAmPm());
        daysOfWeekView.setText(createDayOfWeekString(alarm.getDays()));
        enabledSwitch.setChecked(alarm.getEnabled());
        editAlarmBtn.setTag(R.string.edit_view_expanded_key, false);
        // Set Views: editAlarmView
        timePicker.setCurrentHour(alarm.getStartHours());
        timePicker.setCurrentMinute(alarm.getStartMinutes());
        cbSunday.setChecked(daysEnabled[0]);
        cbMonday.setChecked(daysEnabled[1]);
        cbTuesday.setChecked(daysEnabled[2]);
        cbWednesday.setChecked(daysEnabled[3]);
        cbThursday.setChecked(daysEnabled[4]);
        cbFriday.setChecked(daysEnabled[5]);
        cbSaturday.setChecked(daysEnabled[6]);

        // Listeners for starting/stopping/editing
        editAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // edit alarm container and view
                LinearLayout editAlarmContainer = (LinearLayout) rowView.findViewById(R.id.container);
                // button clicked
                ImageButton btnClicked = (ImageButton) v;
                boolean expanded = (boolean) btnClicked.getTag(R.string.edit_view_expanded_key);

                // toggle edit view
                if(!expanded) {
                    showEditAlarm(rowView, v);
                } else { // must be close btn click

                    // get needed values
                    int hours = timePicker.getCurrentHour();
                    int minutes = timePicker.getCurrentMinute();
                    String displayMinutes = makeDisplayMinutes(minutes);
                    String displayHours = makeDisplayHours(hours);
                    String amPm = amPmFromHours(hours);
                    boolean[] newDays = getRepeatDaysFromEditAlarm(rowView);

                    // update Views
                    hideEditAlarm(rowView, v);
                    timeView.setText(displayHours + ":" + displayMinutes);
                    amPmView.setText(amPm);
                    daysOfWeekView.setText(createDayOfWeekString(newDays));
                    daysOfWeekView.setVisibility(View.VISIBLE);

                    // update alarm object state
                    alarm.setStartHours(hours);
                    alarm.setStartMinutes(minutes);
                    alarm.setAmPm(amPm);
                    alarm.setDays(newDays);

                    if(alarm.getEnabled()) {
                        alarmScheduler.startAlarm(alarm);
                    }
                }
            }
        });
        enabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarm.setEnabled(isChecked);
                if(isChecked) { // switch is on
                    alarmScheduler.startAlarm(alarm);
                } else {
                    alarmScheduler.stopAlarm(alarm);
                    Log.i(TAG, "Alarm " + alarm.getId() + " stopped");
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmList.remove(position);
                AlarmAdapter.this.notifyDataSetChanged();

                // cancel set alarm
                alarmScheduler.stopAlarm(alarm);

                Log.i(TAG, "Alarm " + alarm.getId() + " stopped and deleted");
            }
        });

        return rowView;
    }

    // show the edit alarm for some row in the alarms ListView
    public void showEditAlarm(View rowView, View clicked) {
        ImageButton btnClicked = (ImageButton) clicked;
        LinearLayout editAlarmContainer = (LinearLayout) rowView.findViewById(R.id.container);
        TextView daysOfWeek = (TextView) rowView.findViewById(R.id.days_of_week);
        ImageButton deleteBtn = (ImageButton) rowView.findViewById(R.id.delete_alarm);
        // set toggle tag
        btnClicked.setTag(R.string.edit_view_expanded_key, true);
        // edit alarm visible
        editAlarmContainer.setVisibility(View.VISIBLE);
        // change button
        btnClicked.setImageResource(R.drawable.ic_action_close);
        // show delete ImageButton
        deleteBtn.setVisibility(View.VISIBLE);
        // hide daysOfWeek
        daysOfWeek.setVisibility(View.INVISIBLE);
    }

    public void hideEditAlarm(View rowView, View clicked) {
        ImageButton btnClicked = (ImageButton) clicked;
        LinearLayout editAlarmContainer = (LinearLayout) rowView.findViewById(R.id.container);
        ImageButton deleteBtn = (ImageButton) rowView.findViewById(R.id.delete_alarm);
        // set toggle tag
        btnClicked.setTag(R.string.edit_view_expanded_key, false);
        editAlarmContainer.setVisibility(View.GONE);
        // change button
        btnClicked.setImageResource(R.drawable.ic_action_expand);
        // hide delete ImageButton
        deleteBtn.setVisibility(View.GONE);
    }

    public boolean[] getRepeatDaysFromEditAlarm(final View rowView) {
        List<CheckBox> checkBoxList = new ArrayList<CheckBox>() {{
            add((CheckBox) rowView.findViewById(R.id.cb_sun));
            add((CheckBox) rowView.findViewById(R.id.cb_mon));
            add((CheckBox) rowView.findViewById(R.id.cb_tue));
            add((CheckBox) rowView.findViewById(R.id.cb_wed));
            add((CheckBox) rowView.findViewById(R.id.cb_thu));
            add((CheckBox) rowView.findViewById(R.id.cb_fri));
            add((CheckBox) rowView.findViewById(R.id.cb_sat));
        }};
        boolean[] newDays = new boolean[7];
        for(int i = 0; i < checkBoxList.size(); i++) {
            newDays[i] = checkBoxList.get(i).isChecked();
        }
        return newDays;
    }

    public String makeDisplayHours(int hours) {
        int displayHours = hours;
        if(hours > 11 && hours <= 23) { // is pm
            // minus 12 from hours > 12
            if(hours > 12) {
                displayHours = hours - 12;
            }
        } else { // is am
            if(hours == 0) {
                displayHours = 12;
            }
        }
        return String.valueOf(displayHours);
    }

    public String makeDisplayMinutes(int minutes) {
        String displayMinutes = String.valueOf(minutes);
        // add 0 in front of minutes >= 0 and minutes < 10
        if(displayMinutes.length() == 1) {
            displayMinutes = "0" + displayMinutes;
        }
        return displayMinutes;
    }

    public String amPmFromHours(int hours) {
        if(hours > 11 && hours <= 23) { // is pm
            return Alarm.PM;
        } else { // is am
            return Alarm.AM;
        }
    }

    public String createDayOfWeekString(boolean[] days) {
        String dayOfWeekString = "";
        String[] dayStrings = new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        if(days.length == 7) {
            for(int i = 0; i < days.length; i++) {
                if(days[i]) {
                    dayOfWeekString += dayStrings[i] + ", ";
                }
            }
        }
        if(!dayOfWeekString.isEmpty()) {
            return dayOfWeekString.substring(0, dayOfWeekString.length() - 2);
        } else {
            return "No Repeat";
        }
    }

}
