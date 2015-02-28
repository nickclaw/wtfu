package edu.washington.austindg.wtfu;

import java.io.Serializable;

/**
 * Created by austindg on 2/27/15.
 */
public class Alarm implements Serializable {

    private int startTime;
    private boolean[] days;
    private boolean enabled;

    public Alarm() {
        startTime = 0;
        days = new boolean[7];
        enabled = false;
    }

    public void setStartTime(int time) {
        this.startTime = time;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public boolean[] setDays() {
        return this.days;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

}
