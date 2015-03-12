package edu.washington.austindg.wtfu.wakeup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import edu.washington.austindg.wtfu.DeviceControl;
import edu.washington.austindg.wtfu.R;
import edu.washington.austindg.wtfu.WakeTheFuckUpActivity;
import edu.washington.austindg.wtfu.WakeupActivity;

public class WatchAdActivity extends WakeupActivity {

    public static final String TAG = WatchAdActivity.class.getSimpleName();
    private InterstitialAd mInterstitialAd;
    private Button mNextLevelButton;
    private TextView mTextView;
    private int views;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        views = 0;

        TelephonyManager tm =(TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        // create an ad request.
        final AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

        // Note: May only work on my (austin's) device
        // Will need to test on others
        adRequestBuilder.addTestDevice("16D778BE48D93C26B0DCEE8EECB56D0B");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(WatchAdActivity.this, "Enjoy the Ad, MF", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(adRequestBuilder.build());
                views++;
            }
        });
        mInterstitialAd.loadAd(adRequestBuilder.build());
        setContentView(R.layout.activity_watch_ad);
        Button yesBtn = (Button) findViewById(R.id.yes_btn);
        Button noBtn = (Button) findViewById(R.id.no_btn);
        View.OnClickListener btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (views < 5 && mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    DeviceControl.stopAlarmAudio();
                    done();
                }
            }
        };
        yesBtn.setOnClickListener(btnListener);
        noBtn.setOnClickListener(btnListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(views < 5) {
            finish();
        } else {
            DeviceControl.stopAlarmAudio();
            done();
        }
    }
}
