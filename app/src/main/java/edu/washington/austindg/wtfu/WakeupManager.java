package edu.washington.austindg.wtfu;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.washington.austindg.wtfu.wakeup.AdventureActivity;
import edu.washington.austindg.wtfu.wakeup.ImALittleFatGirlActivity;
import edu.washington.austindg.wtfu.wakeup.WatchAdActivity;

/**
 * Created by nickclaw on 3/11/15.
 */
public class WakeupManager {
    private static WakeupManager instance;
    public static WakeupManager getInstance() {
        if (instance == null) {
            instance = new WakeupManager();
        }
        return instance;
    }

    private static final String TAG = "WakeupManager";
    private Class current = null;
    private List<Class> activities;

    public WakeupManager() {
        this.activities = new ArrayList<Class>(){{
           add(AdventureActivity.class);
           add(ImALittleFatGirlActivity.class);
           add(WatchAdActivity.class);
        }};
    }

    public void wakeup(Activity context) {
        Log.i("RevengeManager", "Launching revenge");
        if (activities.size() == 0) return;
        Class activity = activities.get( (int) (Math.random() * activities.size()) );

        current = activity;
        Intent intent = new Intent(context, activity);
        context.startActivity(intent, null);
    }

    public void restore(Activity context) {
        Log.i(TAG, "Trying to restore");
        if (current != null) {
            // randomly sayInsult user
            Insulter.getInstance().insult();
            Intent intent = new Intent(context, current);
            context.startActivity(intent, null);
        }
    }

    public void put(Class c) {
        current = c;
    }

    public void clear(Class c) {
        if (c == current) {
            current = null;
        }
    }
}
