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

import edu.washington.austindg.wtfu.R;

public class ImALittleFatGirlActivity extends Activity implements RecognitionListener {

    private static final String TAG = "ImALittleFatGirl";

    private SpeechRecognizer sr;
    private View sayItView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_alittle_fat_girl);

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(this);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        sr.startListening(intent);

        sayItView = (View) findViewById(R.id.textView);
    }

    //
    // Speech recognition
    //

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.i(TAG, "onReadyForSpeech");
        sayItView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onResults(Bundle bundle) {
        Log.i(TAG, "onResults");
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        Log.i(TAG, "onPartialResults");
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
