package edu.washington.austindg.wtfu;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

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

    private List<Class<RevengeService>> services;

    public RevengeManager() {
        this.services = new ArrayList<Class<RevengeService>>(){{
            add(RevengeService.class);
        }};
    }

    private void launchService() {
        if (services.size() == 0) return;
        Class<RevengeService> service = services.get( (int) (Math.random() * services.size()));

        Context context = App.getContext();
        Intent intent = new Intent(context, service);
        context.startService(intent);
    }
}
