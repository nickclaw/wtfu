package edu.washington.austindg.wtfu;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.washington.austindg.wtfu.wakeup.WatchAdActivity;


public class WakeTheFuckUpActivity extends WakeupActivity {

    public static final int SEIZURE_DURATION_MS = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // wake up from device sleep and lock
        DeviceControl.wakeDevice(this);

        // turn volume up
        DeviceControl.setDeviceMaxVolume(this);

        // play alarm audio
        DeviceControl.playAlarmAudio(this);

        setContentView(R.layout.activity_wake_the_fuck_up);
        startSeizureLoop();

        Intent launchedMe = getIntent();
        final Alarm alarm = (Alarm) launchedMe.getSerializableExtra("alarm");

        Button snoozeBtn = (Button) findViewById(R.id.snooze_button);
        snoozeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call a revenge action here too
                // Random rand = new Random();
                // int snoozeMins = rand.nextInt(5) + 1; // 0-5 mins
                // App.getAlarmScheduler().startSnoozeForAlarm(alarm, snoozeMins);
                RevengeManager.getInstance().revenge();
                DeviceControl.stopAlarmAudio();
                App.getAlarmScheduler().startSnoozeForAlarm(alarm, 1);
                done();
            }
        });

        Button stopBtn = (Button) findViewById(R.id.stop_button);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch wakeup from here with the alarm that started this
                // stop alarm there with AlarmScheduler methods
                WakeupManager.getInstance().wakeup(WakeTheFuckUpActivity.this);
                // WakeupActivity in charge of turning off alarm
                done();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                finish();
            }
            if (resultCode == RESULT_CANCELED) {
                startWakeupActivity();
            }
        }
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
