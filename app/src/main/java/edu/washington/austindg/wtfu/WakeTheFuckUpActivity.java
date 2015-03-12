package edu.washington.austindg.wtfu;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class WakeTheFuckUpActivity extends Activity {

    public static final int SEIZURE_DURATION_MS = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // wake up from device sleep and lock
        DeviceControl.wakeDevice(this);

        // turn volume up
        DeviceControl.setDeviceMaxVolume(this);

        setContentView(R.layout.activity_wake_the_fuck_up);
        startSeizureLoop();

        MediaPlayer startingMediaPlayer = MediaPlayer.create(this, R.raw.annoying);
        final MediaPlayer loopingMediaPlayer = MediaPlayer.create(this, R.raw.annoying_cut);

        startingMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                loopingMediaPlayer.setLooping(true);
                loopingMediaPlayer.start();
            }

        });

        startingMediaPlayer.start();

        Intent launchedMe = getIntent();
        final Alarm alarm = (Alarm) launchedMe.getSerializableExtra("alarm");

        Button snoozeBtn = (Button) findViewById(R.id.snooze_button);
        snoozeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call a revenge action here too
//                Random rand = new Random();
//                int snoozeMins = rand.nextInt(5) + 1; // 0-5 mins
//                App.getAlarmScheduler().startSnoozeForAlarm(alarm, snoozeMins);
                App.getAlarmScheduler().startSnoozeForAlarm(alarm, 1);
                loopingMediaPlayer.stop();
                finish();
            }
        });

        Button stopBtn = (Button) findViewById(R.id.stop_button);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loopingMediaPlayer.stop();
            }
        });
    }

    public void startSeizureLoop() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.wtfu_text_layout);
        TextView wakeText = (TextView) findViewById(R.id.wake_text);
        TextView theText = (TextView) findViewById(R.id.the_text);
        TextView fuckText = (TextView) findViewById(R.id.fuck_text);
        TextView upText = (TextView) findViewById(R.id.up_text);

        ValueAnimator colorAnim = ObjectAnimator.ofInt(layout, "backgroundColor", Color.WHITE, Color.BLACK);
        colorAnim.setDuration(SEIZURE_DURATION_MS);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim = ObjectAnimator.ofInt(wakeText, "textColor", Color.BLACK, Color.WHITE);
        colorAnim.setDuration(SEIZURE_DURATION_MS);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim = ObjectAnimator.ofInt(theText, "textColor", Color.BLACK, Color.WHITE);
        colorAnim.setDuration(SEIZURE_DURATION_MS);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim = ObjectAnimator.ofInt(fuckText, "textColor", Color.BLACK, Color.WHITE);
        colorAnim.setDuration(SEIZURE_DURATION_MS);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim = ObjectAnimator.ofInt(upText, "textColor", Color.BLACK, Color.WHITE);
        colorAnim.setDuration(SEIZURE_DURATION_MS);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }
}
