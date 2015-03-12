package edu.washington.austindg.wtfu;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by austindg on 3/11/15.
 */
public class DeviceControl {

    private static MediaPlayer mediaPlayer;

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

    public static void playAlarmAudio(Activity activity) {
        // will write code to grab random audio clip in a bit
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(activity, R.raw.annoying);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    public static void pauseAlarmAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public static void continueAlarmAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public static void stopAlarmAudio() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

}
