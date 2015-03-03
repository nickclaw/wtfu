package edu.washington.austindg.wtfu;

import android.app.Application;
import android.content.Context;

/**
 * Created by nickclaw on 3/2/15.
 */
public class App extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }


}
