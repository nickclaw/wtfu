package edu.washington.austindg.wtfu;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by austindg on 3/11/15.
 */
public class DeviceControl {

    public static void wakeDevice(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    public static void setDeviceMaxVolume(Activity activity) {
        AudioManager am = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }

}
