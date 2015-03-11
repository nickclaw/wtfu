package edu.washington.austindg.wtfu.revenge;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.telephony.SmsManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TextRevenge extends IntentService {

    public TextRevenge() {
        super("TextRevenge");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SmsManager sms = SmsManager.getDefault(); // send money to WSU

        String number = "501501";
        String message = "RABIES";
        sms.sendTextMessage(number, null, message, null, null);
    }
}
