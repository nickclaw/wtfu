package edu.washington.austindg.wtfu;

import android.content.Context;
import android.content.res.Resources;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

/**
 * Created by nickclaw on 3/2/15.
 */
public class Insulter implements TextToSpeech.OnInitListener {

    private static final String TAG = "Insulter";

    // singleton code
    private static Insulter instance;
    public static Insulter getInstance() {
        if (instance == null){
            instance = new Insulter();
        }
        return instance;
    }

    private int status;
    private TextToSpeech speaker;

    private Queue<String> queue;
    private List<String> insults;

    private Insulter(){
        Context context = App.getContext();
        Resources res = context.getResources();

        this.speaker = new TextToSpeech(context, this);
        this.status = TextToSpeech.STOPPED;
        this.queue = new LinkedList<String>();


        this.insults = Arrays.asList(res.getStringArray(R.array.insults));

    }

    public void say(String insult) {
        if (!isReady()) {
            queue.add(insult);
            return;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, String.valueOf(1));
        map.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_NOTIFICATION));
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, insult);

        speaker.speak(insult, TextToSpeech.QUEUE_ADD, null);
    }

    public void insult() {
        int index = (int) (Math.random() * this.insults.size());
        String insult = this.insults.get(index);
        Log.i(TAG, "Generated insult: " + insult);
        say(insult);
    }

    /**
     * Returns true if the text2speech instance is initialized
     */
    public boolean isReady() {
        return this.status == TextToSpeech.SUCCESS;
    }

    /**
     * Listens for the text2speech service to be initialized
     */
    public void onInit(int code) {
        this.status = code;
        speaker.setLanguage(Locale.getDefault());

        for(String insult : queue) {
            say(insult);
        }
    }

}
