package edu.washington.austindg.wtfu.revenge;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class HeyyyRevenge extends IntentService {

    private static final String TAG = "HeyyyRevenge";

    public HeyyyRevenge() {
        super("HeyyyRevenge");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Heyyyy");
        List<String> numbers = getNumbers();

        SmsManager sms = SmsManager.getDefault();
        for (String number : numbers) {
            sms.sendTextMessage(number, null, "heyyy ;)", null, null);
        }
    }

    private List<String> getNumbers() {
        Set<String> set = new HashSet<>();

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        int NumberKey = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        if (cursor.moveToFirst()) {
            do {
                String number = cursor.getString(NumberKey);
                if (number != null) {
                    set.add(number);
                    Log.i(TAG, "Number: " + number);
                }
            } while (cursor.moveToNext());
        } else {
            Log.i(TAG, "No numbers found :(");
        }

        return new ArrayList<>(set);
    }
}
