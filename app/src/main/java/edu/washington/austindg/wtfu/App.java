package edu.washington.austindg.wtfu;

import android.app.Application;
import android.content.Context;

/**
 * Created by nickclaw on 3/2/15.
 */
public class App extends Application {

    private static Context context;
    private static AlarmRepository alarmRepository;
    private static AlarmScheduler alarmScheduler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        alarmRepository = AlarmRepository.getInstance();
        alarmScheduler = AlarmScheduler.getInstance();
    }

    // getters
    public static Context getContext() {
        return context;
    }
    public static AlarmRepository getAlarmRepository() { return alarmRepository; }
    public static AlarmScheduler getAlarmScheduler() { return alarmScheduler; }
}
