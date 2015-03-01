package edu.washington.austindg.wtfu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wtfu group
 */
public class AlarmAdapter extends ArrayAdapter<Alarm> {

    private static final String TAG = "AlarmAdapter";
    private final Activity context;
    private final List<Alarm> alarmList;

    public AlarmAdapter(Context context, List<Alarm> alarmList) {

        super(context, R.layout.alarm_item_view, alarmList);

        this.context = (Activity) context;
        this.alarmList = alarmList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.alarm_item_view, parent, false);

        TextView time = (TextView) rowView.findViewById(R.id.time);

        final Alarm alarm = alarmList.get(position);

        Switch enabledSwitch = (Switch) rowView.findViewById(R.id.enabled_switch);
        enabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarm.setEnabled(isChecked);
            }
        });

        Button editBtn = (Button) rowView.findViewById(R.id.edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedBtn = (Button) v;
                EditAlarmFragment editAlarmFragment = new EditAlarmFragment();
                editAlarmFragment.setAlarm(alarm);
                context.getFragmentManager().beginTransaction()
                        .add(R.id.container, editAlarmFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        time.setText(Integer.toString(alarmList.get(position).getStartTime()));
        enabledSwitch.setChecked(alarmList.get(position).getEnabled());

        return rowView;
    }

    public static class EditAlarmFragment extends Fragment {

        private Alarm alarm;

        public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.edit_alarm_layout, container, false);
        }

        public void setAlarm(Alarm alarm) {
            this.alarm = alarm;
        }

    }

}
