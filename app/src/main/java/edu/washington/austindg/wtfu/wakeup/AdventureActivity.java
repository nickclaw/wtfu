package edu.washington.austindg.wtfu.wakeup;

import android.app.Activity;
import android.graphics.Color;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

import edu.washington.austindg.wtfu.DeviceControl;
import edu.washington.austindg.wtfu.R;
import edu.washington.austindg.wtfu.RevengeManager;
import edu.washington.austindg.wtfu.WakeupManager;

public class AdventureActivity extends Activity
        implements GoogleApiClient.ConnectionCallbacks,
                   GoogleApiClient.OnConnectionFailedListener,
                   com.google.android.gms.location.LocationListener {

    private static final String TAG = "AdventureActivity";

    private GoogleApiClient googleApiClient;
    private GoogleMap map;

    private Location startingLoc = null;
    private Location destLoc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Starting..");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

    public void onConnectionSuspended(int code) {
        Log.i(TAG, "Connection suspended: " + code);
        // act of revenge instead
        RevengeManager.getInstance().revenge();
        finish();
    }

    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: " + result.getErrorCode());
        // act of revenge instead
        RevengeManager.getInstance().revenge();
        finish();
    }

    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Connection made");
        LocationRequest request = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(4000)
            .setFastestInterval(2000);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, this);
    }

    public void onLocationChanged(Location loc) {
        Log.i(TAG, "Loc: " + loc.toString());

        // case: first location
        if (startingLoc == null) {
            Log.i(TAG, "First location!");
            startingLoc = loc;
            destLoc = getNearbyLatLng(loc);

            // animate camera to location
            CameraPosition pos = CameraPosition.builder()
                    .target(new LatLng(loc.getLatitude(), loc.getLongitude()))
                    .zoom(15.9f)
                    .build();
            CameraUpdate moveTo = CameraUpdateFactory.newCameraPosition(pos);
            map.animateCamera(moveTo);


            // place to go location
            CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(destLoc.getLatitude(), destLoc.getLongitude()))
                .fillColor(Color.GREEN)
                .strokeColor(Color.BLACK)
                .radius(100.0);
            map.addCircle(circleOptions);

        // case: moving towards destLoc
        } else {

            // stop alarm if close enough
            Log.i(TAG, "Loc: " + loc.toString() + ", " + loc.distanceTo(destLoc) + "m to go");
            if (loc.distanceTo(destLoc) < 100.0f) {
                Log.i(TAG, "Reached destination");
                DeviceControl.stopAlarmAudio();
                finish();
            }
        }
    }

    // from http://gis.stackexchange.com/a/68275
    private Location getNearbyLatLng(Location loc) {
        Random random = new Random();

        // Convert radius from meters to degrees
        double radiusInDegrees = 600 / 111000f;

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of the east-west distances
        double new_x = x / Math.cos(loc.getLongitude());

        double foundLongitude = new_x + loc.getLatitude();
        double foundLatitude = y + loc.getLongitude();

        Log.i(TAG, "Lat: " + foundLatitude + ", Long: " + foundLongitude);

        Location newLoc = new Location(loc);
        newLoc.setLatitude(foundLongitude);
        newLoc.setLongitude(foundLatitude);
        return newLoc;
    }
}
