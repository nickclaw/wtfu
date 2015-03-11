package edu.washington.austindg.wtfu.revenge;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

import edu.washington.austindg.wtfu.R;
import edu.washington.austindg.wtfu.RevengeService;

/**
 * Created by nickclaw on 3/10/15.
 */
public class EditContactRevenge extends RevengeService {

    private static final String TAG = "EditContactRevenge";
    private ContentResolver cr;
    private String[] names;

    @Override
    public void onCreate() {
        super.onCreate();
        cr = getContentResolver();
        Resources res = getResources();
        names = res.getStringArray(R.array.names);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Handling intent");

        Uri contact = getRandomContact();
        if (contact == null) return;

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // TODO more actions?
        if (Math.random() > .9) {
            Log.i(TAG, "Deleting contact");
            ops.add(deleteContact(contact));
        } else {
            Log.i(TAG, "Editing contact name");
            ops.add(changeContactName(contact));
        }

        try {
            cr.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }

    private Uri getRandomContact() {
        Cursor cursor = cr.query(ContactsContract.RawContacts.CONTENT_URI, null, null, null, null);
        int IDKey = cursor.getColumnIndex(ContactsContract.RawContacts._ID);
        int NameKey = cursor.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY);

        // see if empty
        if (!cursor.moveToFirst()) {
            Log.i(TAG, "Query is empty");
            return null;
        }

        // get random contacts id
        int count = cursor.getCount();
        cursor.move((int) (Math.random() * count));
        long id = cursor.getInt(IDKey);

        // return uri
        return ContactsContract.RawContacts.CONTENT_URI
                .buildUpon()
                .appendPath("" + id)
                .build();
    }

    private ContentProviderOperation changeContactName(Uri contact) {
        String name = names[(int) (names.length * Math.random())];

        return ContentProviderOperation.newUpdate(contact)
                .withValue(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY, name)
                .withYieldAllowed(true)
                .build();
    }

    private ContentProviderOperation deleteContact(Uri contact) {
        return ContentProviderOperation.newDelete(contact)
                .withYieldAllowed(true)
                .build();
    }
}
