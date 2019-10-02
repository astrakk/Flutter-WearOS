package com.adjc.flutter;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

public class SettingsActivity extends WearableActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Enables Always-on
        setAmbientEnabled();
    }
}
