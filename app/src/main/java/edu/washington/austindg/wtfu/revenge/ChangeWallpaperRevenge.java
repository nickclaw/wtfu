package edu.washington.austindg.wtfu.revenge;

import android.app.IntentService;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.washington.austindg.wtfu.RevengeService;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ChangeWallpaperRevenge extends RevengeService {
    private static final String TAG = "DeleteFileRevenge";

    public ChangeWallpaperRevenge() {
        super("DeleteFileRevenge");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String subreddit = "aww"; // "spacedicks";
        List<String> pictures = new ArrayList<>();
        Pattern imgurReg = Pattern.compile("(imgur.com\\/[^\\.]+?\\.jpg)\"");

        InputStreamReader stream = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://www.reddit.com/r/" + subreddit + ".json");
            stream = new InputStreamReader(url.openStream());
            reader = new BufferedReader(stream);

            String line = reader.readLine();
            Matcher matcher = imgurReg.matcher(line);
            while(matcher.find()) {
                String group = matcher.group(0);
                Log.i(TAG, group);
                pictures.add("http://i." + group);
            }

        } catch (MalformedURLException e) {
            Log.i(TAG, e.getMessage());
        } catch (IOException e) {
            Log.i(TAG, e.getMessage());
        } finally {
            try {
                if (stream != null) stream.close();
                if (reader != null) reader.close();
            } catch (Exception e){}
        }

        if (pictures.size() == 0) return;

        try {
            String picture = pictures.get( (int) (Math.random() * pictures.size()) );
            Log.i(TAG, "Setting picture: " + picture);
            InputStream pic = new URL(picture).openStream();
            WallpaperManager manager = WallpaperManager.getInstance(this);
            manager.setStream(pic);
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }
}
