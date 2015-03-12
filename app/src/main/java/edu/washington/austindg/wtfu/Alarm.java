package edu.washington.austindg.wtfu;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by austindg on 2/27/15.
 */
public class Alarm implements Serializable {

    private static final String TAG = "Alarm";
    public static final String AM = "AM";
    public static final String PM = "PM";

    private int startHours;
    private int startMinutes;
    private String amPm;
    private boolean[] days;
    private boolean enabled;
    private int id;

    public Alarm() {
        this(8, 30, AM, new boolean[7], false);
    }

    public Alarm(int startHours, int startMinutes, String amPm, boolean[] days, boolean enabled) {
        this.startHours = startHours;
        this.startMinutes = startMinutes;
        this.amPm = amPm;
        this.days = days;
        this.enabled = enabled;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setStartHours(int hours) {
        this.startHours = hours;
    }

    public int getStartHours() {
        return this.startHours;
    }

    public void setStartMinutes(int mins) {
        this.startMinutes = mins;
    }

    public int getStartMinutes() {
        return this.startMinutes;
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

    public String toString() {
        return "Alarm " + getId() + ": {hour: " + getStartHours() +
                ", minutes: " + getStartMinutes() +
                ", daysEnabled: " + Arrays.toString(getDays()) +
                "}";
    }
}
