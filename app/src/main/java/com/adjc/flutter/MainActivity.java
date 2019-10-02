package com.adjc.flutter;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
