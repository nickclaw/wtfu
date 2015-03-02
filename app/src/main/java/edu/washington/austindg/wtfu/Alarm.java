package edu.washington.austindg.wtfu;

import java.io.Serializable;

/**
 * Created by austindg on 2/27/15.
 */
public class Alarm implements Serializable {

    public static final String AM = "AM";
    public static final String PM = "PM";

    private int startTime;
    private String amPm;
    private boolean[] days;
    private boolean enabled;

    public Alarm() {
        this(420, PM, new boolean[7], false);
    }

    public Alarm(int startTime, String amPm, boolean[] days, boolean enabled) {
        this.startTime = startTime;
        this.amPm = amPm;
        this.days = days;
        this.enabled = enabled;
    }

    public void setStartTime(int time) {
        this.startTime = time;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }

    public String getAmPm() {
        return this.amPm;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public boolean[] getDays() {
        return this.days;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

}
