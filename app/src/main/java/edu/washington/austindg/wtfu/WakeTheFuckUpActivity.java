package edu.washington.austindg.wtfu;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class WakeTheFuckUpActivity extends Activity {

    public static final int STARTING_SNOOZE_ID = 2000;
    public static final int SEIZURE_DURATION = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // wake up from device sleep and lock
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_wake_the_fuck_up);
        startSeizureLoop();

        Intent launchedMe = getIntent();
        final Alarm alarm = (Alarm) launchedMe.getSerializableExtra("alarm");

        Button snoozeBtn = (Button) findViewById(R.id.snooze_button);
        snoozeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Alarm snoozeAlarm = new Alarm()
                //App.getAlarmScheduler().startAlarm();
            }
        });

        Button stopBtn = (Button) findViewById(R.id.stop_button);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void startSeizureLoop() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.wtfu_text_layout);
        Button snoozeBtn = (Button) findViewById(R.id.snooze_button);
        Button stopBtn = (Button) findViewById(R.id.stop_button);
        TextView wakeText = (TextView) findViewById(R.id.wake_text);
        TextView theText = (TextView) findViewById(R.id.the_text);
        TextView fuckText = (TextView) findViewById(R.id.fuck_text);
        TextView upText = (TextView) findViewById(R.id.up_text);

        ValueAnimator colorAnim = ObjectAnimator.ofInt(layout, "backgroundColor", Color.WHITE, Color.BLACK);
        colorAnim.setDuration(SEIZURE_DURATION);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim = ObjectAnimator.ofInt(snoozeBtn, "backgroundColor", Color.WHITE, Color.BLACK);
        colorAnim.setDuration(SEIZURE_DURATION);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim = ObjectAnimator.ofInt(snoozeBtn, "textColor", Color.BLACK, Color.WHITE);
        colorAnim.setDuration(SEIZURE_DURATION);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim = ObjectAnimator.ofInt(stopBtn, "backgroundColor", Color.WHITE, Color.BLACK);
        colorAnim.setDuration(SEIZURE_DURATION);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim = ObjectAnimator.ofInt(stopBtn, "textColor", Color.BLACK, Color.WHITE);
        colorAnim.setDuration(SEIZURE_DURATION);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim = ObjectAnimator.ofInt(wakeText, "textColor", Color.BLACK, Color.WHITE);
        colorAnim.setDuration(SEIZURE_DURATION);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim = ObjectAnimator.ofInt(theText, "textColor", Color.BLACK, Color.WHITE);
        colorAnim.setDuration(SEIZURE_DURATION);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim = ObjectAnimator.ofInt(fuckText, "textColor", Color.BLACK, Color.WHITE);
        colorAnim.setDuration(SEIZURE_DURATION);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        colorAnim = ObjectAnimator.ofInt(upText, "textColor", Color.BLACK, Color.WHITE);
        colorAnim.setDuration(SEIZURE_DURATION);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }
}
