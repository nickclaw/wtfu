package edu.washington.austindg.wtfu;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public abstract class WakeupActivity extends Activity {

    public void done() {
        WakeupManager.getInstance().clear(this.getClass());
        super.finish();
    }
}
