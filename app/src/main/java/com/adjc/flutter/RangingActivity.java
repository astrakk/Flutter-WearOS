package com.adjc.flutter;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.service.RunningAverageRssiFilter;

import java.util.Collection;

public class RangingActivity extends Activity implements BeaconConsumer {
    protected static final String TAG = "RangingActivity";
    private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranging);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        // Detect proprietary iBeacons
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
    @Override
    public void onBeaconServiceConnect() {
        beaconManager.removeAllRangeNotifiers();
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                // Initialize the vibrator device
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

                // Get the known beacon hardware IDs
                String beacon0Id = MainActivity.configs.getBeacon0Id();
                String beacon1Id = MainActivity.configs.getBeacon1Id();

                // Reset the nearby beacons counter
                int nearbyBeacons = 0;
                TextView textView_beacons = findViewById(R.id.textView_beacons);
                textView_beacons.setText("Beacons in range: " + nearbyBeacons);

                // Iterate over all beacons, logging their distances
                for (Beacon beacon: beacons) {
                    // Store the detected beacon's hardware ID for comparison with known beacons
                    String detectedBeaconId = beacon.getBluetoothAddress();

                    // If beacon is within 0.5 metres, vibrate and display on screen
                    if (beacon.getDistance() * 10.0 < 0.5) {
                        // Increment the nearby beacons value. Useful for detected unknown beacons as well.
                        nearbyBeacons++;

                        // Compare the hardware ID of the detected beacon with known ones, vibrating appropriately
                        if (detectedBeaconId.compareTo(beacon0Id) == 0) {
                            vibrator.vibrate(MainActivity.configs.getBeacon0VibrationPattern(), -1);
                        }
                        else if (detectedBeaconId.compareTo(beacon1Id) == 0) {
                            vibrator.vibrate(MainActivity.configs.getBeacon1VibrationPattern(), -1);
                        }

                        // Update the text on screen to represent the number of beacons nearby
                        textView_beacons.setText("Beacons in range: " + nearbyBeacons);
                    }
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            // Reduce the sample size from 20s to 5s. Reduces accuracy but improves speed.
            BeaconManager.setRssiFilterImplClass(RunningAverageRssiFilter.class);
            RunningAverageRssiFilter.setSampleExpirationMilliseconds(5000l);

            // Register beacons by hardware ID instead of UUID (thanks Apple...)
            Beacon.setHardwareEqualityEnforced(true);
        } catch (RemoteException e) {    }
    }
}


