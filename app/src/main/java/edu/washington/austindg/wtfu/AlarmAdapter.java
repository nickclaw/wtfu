package edu.washington.austindg.wtfu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

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
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.alarm_item_view, parent, false);
        final LinearLayout editAlarmContainer = (LinearLayout) rowView.findViewById(R.id.container);

        // editAlarmView will be added to editAlarmContainer in onClickListener for editAlarmBtn
        final View editAlarmView = inflater.inflate(R.layout.edit_alarm_layout, null);

        final Alarm alarm = alarmList.get(position);

        TextView time = (TextView) rowView.findViewById(R.id.time);
        time.setText(Integer.toString(alarmList.get(position).getStartTime()));

        TextView amPm = (TextView) rowView.findViewById(R.id.am_pm);
        amPm.setText(alarm.getAmPm());

        final TextView daysOfWeek = (TextView) rowView.findViewById(R.id.days_of_week);
        daysOfWeek.setText(createDayOfWeekString(alarm.getDays()));

        Switch enabledSwitch = (Switch) rowView.findViewById(R.id.enabled_switch);
        enabledSwitch.setChecked(alarm.getEnabled());
        enabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarm.setEnabled(isChecked);
            }
        });

        final ImageButton editAlarmBtn = (ImageButton) rowView.findViewById(R.id.edit_alarm_btn);
        editAlarmBtn.setTag(R.string.edit_view_expanded_key, false);

        final ImageButton deleteBtn = (ImageButton) rowView.findViewById(R.id.delete_alarm);

        editAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm clickedAlarm = alarmList.get(position);

                ImageButton btnClicked = (ImageButton) v;
                boolean expanded = (boolean) btnClicked.getTag(R.string.edit_view_expanded_key);
                if(!expanded) {
                    // change button and set toggle tag
                    btnClicked.setImageResource(R.drawable.ic_action_close);
                    btnClicked.setTag(R.string.edit_view_expanded_key, true);

                    // show delete ImageButton
                    deleteBtn.setVisibility(View.VISIBLE);

                    // hide daysOfWeek
                    daysOfWeek.setVisibility(View.INVISIBLE);

                    // make editAlarmView visible and animate Y
                    editAlarmContainer.addView(editAlarmView, editAlarmContainer.getChildCount());
                    editAlarmView.setVisibility(View.VISIBLE);
                    editAlarmView.animate().y(20).setDuration(200).start();
                } else { // must be close btn click
                    // change button and set toggle tag
                    btnClicked.setTag(R.string.edit_view_expanded_key, false);
                    btnClicked.setImageResource(R.drawable.ic_action_expand);

                    // hide delete ImageButton
                    deleteBtn.setVisibility(View.GONE);

                    // show daysOfWeek
                    daysOfWeek.setVisibility(View.VISIBLE);

                    // set editAlarmView Y back and hide
                    editAlarmView.setTranslationY(-20);
                    editAlarmContainer.removeView(editAlarmView);
                    editAlarmView.setVisibility(View.INVISIBLE);
                }
            }
        });

        return rowView;
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
