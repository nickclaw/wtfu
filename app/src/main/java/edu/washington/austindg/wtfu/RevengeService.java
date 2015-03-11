package edu.washington.austindg.wtfu;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public abstract class RevengeService extends IntentService {

    public RevengeService(String name) {super(name);}
    public RevengeService() {
        super("RevengeService");
    }

    // util functions?
}
