package edu.washington.austindg.wtfu;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.washington.austindg.wtfu.revenge.ChangeWallpaperRevenge;
import edu.washington.austindg.wtfu.revenge.EditContactRevenge;
import edu.washington.austindg.wtfu.revenge.TextRevenge;

/**
 * Created by nickclaw on 3/9/15.
 */
public class RevengeManager {

    private static RevengeManager instance;
    public static RevengeManager getInstance() {
        if (instance == null) {
            instance = new RevengeManager();
        }
        return instance;
    }

    private List<Class> services;

    public RevengeManager() {
        this.services = new ArrayList<Class>(){{
            add(EditContactRevenge.class);
            add(ChangeWallpaperRevenge.class);
            add(TextRevenge.class);
        }};
    }

    public void revenge() {
        Log.i("RevengeManager", "Launching revenge");
        if (services.size() == 0) return;
        Class service = services.get( (int) (Math.random() * services.size()) );

        Context context = App.getContext();
        Intent intent = new Intent(context, service);
        context.startService(intent);
    }
}
