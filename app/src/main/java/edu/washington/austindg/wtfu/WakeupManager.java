package edu.washington.austindg.wtfu;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.washington.austindg.wtfu.revenge.ChangeWallpaperRevenge;
import edu.washington.austindg.wtfu.revenge.EditContactRevenge;
import edu.washington.austindg.wtfu.revenge.HeyyyRevenge;
import edu.washington.austindg.wtfu.revenge.TextRevenge;
import edu.washington.austindg.wtfu.wakeup.AdventureActivity;

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

    private List<Class> activities;

    public WakeupManager() {
        this.activities = new ArrayList<Class>(){{
           add(AdventureActivity.class);
        }};
    }

    public void wakeup() {
        Log.i("RevengeManager", "Launching revenge");
        if (activities.size() == 0) return;
        Class service = activities.get( (int) (Math.random() * activities.size()) );

        Context context = App.getContext();
        Intent intent = new Intent(context, service);
        context.startActivity(intent, null);
    }
}