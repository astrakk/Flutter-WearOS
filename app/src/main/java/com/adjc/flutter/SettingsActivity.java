package com.adjc.flutter;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.List;

public class SettingsActivity extends WearableActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Enables Always-on
        setAmbientEnabled();

        // Appropriately size the beacon 0 spinner
        Spinner spinner_beacon0 = findViewById(R.id.spinner_beacon0);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner_beacon0);

            // Set popupWindow height to 500px
            popupWindow.setHeight(250);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        // Add the vibration list to the beacon 0 spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.style_custom_spinner, MainActivity.configs.getVibrationPatternsArrayPretty());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_beacon0.setAdapter(spinnerArrayAdapter);
    }
}
