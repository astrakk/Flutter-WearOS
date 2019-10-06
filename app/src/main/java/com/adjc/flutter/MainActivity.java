package com.adjc.flutter;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends WearableActivity {
    // Define the vibration patterns list
    public List<long[]> vibrationPatterns = new ArrayList<long[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the patterns and add them to the list
        long[] pattern0 = {0, 500, 50, 300};
        long[] pattern1 = {0, 200, 25, 200, 25, 500};
        long[] pattern2 = {0, 600, 20, 100};
        long[] pattern3 = {0, 150, 50, 150, 50, 150, 300};
        long[] pattern4 = {0, 600, 50, 600, 50, 100};
        vibrationPatterns.add(pattern0);
        vibrationPatterns.add(pattern1);
        vibrationPatterns.add(pattern2);
        vibrationPatterns.add(pattern3);
        vibrationPatterns.add(pattern4);

        setContentView(R.layout.activity_main);

        // Enables Always-on
        setAmbientEnabled();
    }

    /** Start the ranging activity **/
    public void startRangingActivity(View view) {
        // Call the ranging activity to begin ranging all nearby beacons
        Intent ranging_intent = new Intent(this, RangingActivity.class);
        startActivity(ranging_intent);
    }

    /** Start the settings activity **/
    public void startSettingsActivity(View view) {
        // Call the settings activity
        Intent settings_intent = new Intent(this, SettingsActivity.class);
        startActivity(settings_intent);
    }
}
