package edu.washington.austindg.wtfu;

import android.app.Activity;
import android.content.Context;
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
    private final Activity activity;
    private final List<Alarm> alarmList;

    public AlarmAdapter(Activity activity, List<Alarm> alarmList) {

        super(activity, R.layout.alarm_item_view, alarmList);

        this.activity = activity;
        this.alarmList = alarmList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Alarm alarm = alarmList.get(position);

        final LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.alarm_item_view, parent, false);
        final View editAlarmView = inflater.inflate(R.layout.edit_alarm_layout, null);

        // Views in rowView
        final TextView daysOfWeek = (TextView) rowView.findViewById(R.id.days_of_week);
        final ImageButton editAlarmBtn = (ImageButton) rowView.findViewById(R.id.edit_alarm_btn);
        final ImageButton deleteBtn = (ImageButton) rowView.findViewById(R.id.delete_alarm);
        Switch enabledSwitch = (Switch) rowView.findViewById(R.id.enabled_switch);

        // Set Views for rowView
        setAlarmTimeUI(rowView, alarm, alarm.getStartHours(), alarm.getStartMinutes());
        daysOfWeek.setText(createDayOfWeekString(alarm.getDays()));
        enabledSwitch.setChecked(alarm.getEnabled());
        editAlarmBtn.setTag(R.string.edit_view_expanded_key, false);

        // listeners for rowView
        enabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarm.setEnabled(isChecked);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmList.remove(position);
                AlarmAdapter.this.notifyDataSetChanged();
            }
        });

        // Views in editAlarmView
        final TimePicker timePicker = (TimePicker) editAlarmView.findViewById(R.id.time_picker);
        final CheckBox cbMonday = (CheckBox) editAlarmView.findViewById(R.id.cb_mon);
        final CheckBox cbTuesday = (CheckBox) editAlarmView.findViewById(R.id.cb_tue);
        final CheckBox cbWednesday = (CheckBox) editAlarmView.findViewById(R.id.cb_wed);
        final CheckBox cbThursday = (CheckBox) editAlarmView.findViewById(R.id.cb_thu);
        final CheckBox cbFriday = (CheckBox) editAlarmView.findViewById(R.id.cb_fri);
        final CheckBox cbSaturday = (CheckBox) editAlarmView.findViewById(R.id.cb_sat);
        final CheckBox cbSunday = (CheckBox) editAlarmView.findViewById(R.id.cb_sun);
        final ArrayList<CheckBox> checkBoxList = new ArrayList<CheckBox>() {{
            add(cbMonday);
            add(cbTuesday);
            add(cbWednesday);
            add(cbThursday);
            add(cbFriday);
            add(cbSaturday);
            add(cbSunday);
        }};

        // Set Views for editAlarmView
        timePicker.setCurrentHour(alarm.getStartHours());
        timePicker.setCurrentMinute(alarm.getStartMinutes());

        boolean[] daysEnabled = alarm.getDays();
        cbMonday.setChecked(daysEnabled[0]);
        cbTuesday.setChecked(daysEnabled[1]);
        cbWednesday.setChecked(daysEnabled[2]);
        cbThursday.setChecked(daysEnabled[3]);
        cbFriday.setChecked(daysEnabled[4]);
        cbSaturday.setChecked(daysEnabled[5]);
        cbSunday.setChecked(daysEnabled[6]);

        editAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // edit alarm container and view
                LinearLayout editAlarmContainer = (LinearLayout) rowView.findViewById(R.id.container);

                // button clicked
                ImageButton btnClicked = (ImageButton) v;
                boolean expanded = (boolean) btnClicked.getTag(R.string.edit_view_expanded_key);

                if(!expanded) {
                    // set toggle tag
                    btnClicked.setTag(R.string.edit_view_expanded_key, true);

                    // change button
                    btnClicked.setImageResource(R.drawable.ic_action_close);

                    // show delete ImageButton
                    deleteBtn.setVisibility(View.VISIBLE);

                    // hide daysOfWeek
                    daysOfWeek.setVisibility(View.INVISIBLE);

                    // make editAlarmView visible and animate Y
                    editAlarmContainer.addView(editAlarmView, editAlarmContainer.getChildCount());
                    editAlarmView.setVisibility(View.VISIBLE);
                    editAlarmView.animate().y(20).setDuration(200).start();
                } else { // must be close btn click
                    // set toggle tag
                    btnClicked.setTag(R.string.edit_view_expanded_key, false);

                    // change button
                    btnClicked.setImageResource(R.drawable.ic_action_expand);

                    // hide delete ImageButton
                    deleteBtn.setVisibility(View.GONE);

                    // set editAlarmView Y back and hide
                    editAlarmView.setTranslationY(-20);
                    editAlarmContainer.removeView(editAlarmView);
                    editAlarmView.setVisibility(View.INVISIBLE);

                    // update alarm with checkbox values
                    boolean[] newDays = new boolean[7];
                    for(int i = 0; i < checkBoxList.size(); i++) {
                        newDays[i] = checkBoxList.get(i).isChecked();
                    }
                    alarm.setDays(newDays);

                    // change and show daysOfWeek
                    daysOfWeek.setText(createDayOfWeekString(newDays));
                    daysOfWeek.setVisibility(View.VISIBLE);

                    // update new alarm start
                    int hours = timePicker.getCurrentHour();
                    int minutes = timePicker.getCurrentMinute();
                    alarm.setStartHours(hours);
                    alarm.setStartMinutes(minutes);

                    // update UI
                    setAlarmTimeUI(rowView, alarm, hours, minutes);

                    // start alarm
                    startAlarm(alarm);
                }
            }
        });

        return rowView;
    }

    public void startAlarm(Alarm alarm) {

//        long alarmStartHours = alarm.getStartHours();
//        long alarmStartMinutes = alarm.getStartMinutes();
//
//        long alarmHoursMs = alarmStartHours * 60 * 60 * 1000;
//        long alarmMinutesMs = alarmStartMinutes * 60 * 1000;
//
//        long currentTimeMs = System.currentTimeMillis();
//        long alarmTimeMs = alarmHoursMs + alarmMinutesMs;
//
//        Intent alarmIntervalIntent = new Intent(activity, StartAlarmReceiver.class);
//        alarmIntervalIntent.putExtra("alarm", alarm);
//        PendingIntent pendingAlarm = PendingIntent.getBroadcast(activity, 0, alarmIntervalIntent, 0);
//        AlarmManager manager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, , AlarmManager.INTERVAL_DAY, pendingAlarm);
    }

    public void setAlarmTimeUI(View rowView, Alarm alarm, int hours, int minutes) {
        final TextView time = (TextView) rowView.findViewById(R.id.time);
        final TextView amPm = (TextView) rowView.findViewById(R.id.am_pm);

        // update UI
        int displayHours = hours;
        if(hours > 11 && hours <= 23) { // is pm
            alarm.setAmPm(Alarm.PM);
            amPm.setText(alarm.getAmPm());
            // minus 12 from hours > 12
            if(hours > 12) {
                displayHours = hours - 12;
            }
        } else { // is am
            alarm.setAmPm(Alarm.AM);
            amPm.setText(alarm.getAmPm());
            if(hours == 0) {
                displayHours = 12;
            }
        }
        String displayMinutes = String.valueOf(minutes);
        // add 0 in front of minutes >= 0 and minutes < 10
        if(displayMinutes.length() == 1) {
            displayMinutes = "0" + displayMinutes;
        }
        time.setText(String.valueOf(displayHours) + ":" + String.valueOf(displayMinutes));
    }

    public String createDayOfWeekString(boolean[] days) {
        String dayOfWeekString = "";
        String[] dayStrings = new String[] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
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
