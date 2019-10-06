package com.adjc.flutter;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Field;

public class SettingsActivity extends WearableActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Enables Always-on
        setAmbientEnabled();

        // Get the vibrator service
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        /**
         * BEACON 0 SPINNER SETUP BEGINS HERE
         */
        // Appropriately size the beacon 0 spinner
        Spinner spinner_beacon0 = findViewById(R.id.spinner_beacon0);
        try {
            Field popup_beacon0 = Spinner.class.getDeclaredField("mPopup");
            popup_beacon0.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow_beacon0 = (android.widget.ListPopupWindow) popup_beacon0.get(spinner_beacon0);

            // Set popupWindow_beacon0 height to 500px
            popupWindow_beacon0.setHeight(250);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // Fail silently
        }

        // Add the vibration list to the beacon 0 spinner
        ArrayAdapter<String> beacon0ArrayAdapter = new ArrayAdapter<>(this, R.layout.style_custom_spinner, MainActivity.configs.getVibrationPatternsArrayPretty());
        beacon0ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_beacon0.setAdapter(beacon0ArrayAdapter);

        spinner_beacon0.setSelection(MainActivity.configs.getBeacon0VibrationId());

        // Set up the code to run when an item is selected from the spinner
        spinner_beacon0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.configs.setBeacon0VibrationId(i);
                vibrator.vibrate(MainActivity.configs.getBeacon0VibrationPattern(), -1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /**
         * BEACON 1 SPINNER SETUP BEGINS HERE
         */
        // Appropriately size the beacon 0 spinner
        Spinner spinner_beacon1 = findViewById(R.id.spinner_beacon1);
        try {
            Field popup_beacon1 = Spinner.class.getDeclaredField("mPopup");
            popup_beacon1.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow_beacon1 = (android.widget.ListPopupWindow) popup_beacon1.get(spinner_beacon1);

            // Set popupWindow_beacon1 height to 500px
            popupWindow_beacon1.setHeight(250);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // Fail silently
        }

        // Add the vibration list to the beacon 0 spinner
        ArrayAdapter<String> beacon1ArrayAdapter = new ArrayAdapter<>(this, R.layout.style_custom_spinner, MainActivity.configs.getVibrationPatternsArrayPretty());
        beacon1ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_beacon1.setAdapter(beacon1ArrayAdapter);

        spinner_beacon1.setSelection(MainActivity.configs.getBeacon1VibrationId());

        // Set up the code to run when an item is selected from the spinner
        spinner_beacon1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.configs.setBeacon1VibrationId(i);
                vibrator.vibrate(MainActivity.configs.getBeacon1VibrationPattern(), -1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
