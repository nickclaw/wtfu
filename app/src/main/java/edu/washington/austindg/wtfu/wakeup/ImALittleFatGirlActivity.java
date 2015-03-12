package edu.washington.austindg.wtfu.wakeup;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.washington.austindg.wtfu.DeviceControl;
import edu.washington.austindg.wtfu.R;
import edu.washington.austindg.wtfu.WakeupActivity;

public class ImALittleFatGirlActivity extends WakeupActivity implements RecognitionListener, View.OnClickListener {

    private static final String TAG = "ImALittleFatGirl";

    private SpeechRecognizer sr;
    private boolean isListening = false;
    private Button btn;
    private TextView partial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_alittle_fat_girl);

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(this);

        DeviceControl.pauseAlarmAudio();

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say it");

        sr.startListening(intent);
        isListening = true;

        partial = (TextView) findViewById(R.id.partial);
        btn = (Button) findViewById(R.id.tryAgainButton);
        btn.setEnabled(false);
        btn.setOnClickListener(this);
    }

    // click listener
    public void onClick(View v) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        DeviceControl.pauseAlarmAudio();
        sr.startListening(intent);
        isListening = true;
        btn.setEnabled(false);
    }

    //
    // Speech recognition
    //

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.i(TAG, "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onResults(Bundle bundle) {
        Log.i(TAG, "onResults");
        if (isListening) {
            isListening = false;
            btn.setEnabled(true);

            ArrayList<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for(String s : results) {
                Log.i(TAG, "Result: " + s);
                if (s.contains("fat") && s.contains("little") && s.contains("girl")) {
                    partial.setText(s);
                    Log.i(TAG, "Worked!");
                    DeviceControl.stopAlarmAudio();
                    done();
                    sr.destroy();
                    Toast.makeText(this, "That's right, fatty", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            DeviceControl.continueAlarmAudio();
        }
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        Log.i(TAG, "onPartialResults");
        ArrayList<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (results.size() > 0) {
            partial.setText(results.get(0));
        }
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(TAG, "onBufferedReceived");
    }

    @Override
    public void onEvent(int status, Bundle bundle) {
        Log.i(TAG, "onEvent");
    }

    @Override
    public void onError(int code) {
        Log.i(TAG, "onError");
        if (isListening) {
            Toast.makeText(this, "Try again, fatty", Toast.LENGTH_SHORT).show();
            isListening = false;
            btn.setEnabled(true);
            DeviceControl.continueAlarmAudio();
        }
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(TAG, "onEndOfSpeech");
    }

    //

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(TAG, "onRmsChanged");
    }
}
