package edu.washington.austindg.wtfu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by austindg on 3/1/15.
 */
public class EditAlarmFragment extends Fragment {

    private Alarm alarm;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_alarm_layout, container, false);
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

}
