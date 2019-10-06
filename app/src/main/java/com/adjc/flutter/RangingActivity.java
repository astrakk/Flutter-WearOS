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

                // Reset the nearby beacons counter
                int nearbyBeacons = 0;
                TextView textView_beacons = findViewById(R.id.textView_beacons);
                textView_beacons.setText("Beacons in range: " + nearbyBeacons);

                // Define the testing vibration pattern
                long[] vibrationPattern = {0, 500, 25, 50, 25, 50};


                // Iterate over all beacons, logging their distances
                for (Beacon beacon: beacons) {
                    nearbyBeacons++;
                    // If beacon is within 0.5 metres, vibrate and display on screen
                    if (beacon.getDistance() * 10.0 < 0.5) {
                        String test = beacon.getBluetoothAddress();
                        String test2 = MainActivity.configs.getBeacon0Id();

                        if (test.compareTo(test2) == 0) {
                            Log.println(Log.INFO,TAG, "AAAAAAAAAAAAAA THE ID MATCHEC AAAAAAAAAAA:    " + MainActivity.configs.getBeacon0VibrationId());
                        }

                        Log.println(Log.INFO, TAG, "HERE IS THE LINE: " + MainActivity.configs.getBeacon0Id());
                        //nearbyBeacons++;
                        textView_beacons.setText("Beacons in range: " + nearbyBeacons);
                        vibrator.vibrate(vibrationPattern, -1);
                    }

                    Log.println(Log.INFO, TAG, "Distance is " + beacon.getDistance() * 10.0);
                    Log.println(Log.INFO, TAG, "ID is: " + beacon.getBluetoothAddress() + " (" + nearbyBeacons + ")");
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            // Reduce the sample size from 20s to 5s. Reduces accuracy but improves speed.
            BeaconManager.setRssiFilterImplClass(RunningAverageRssiFilter.class);
            RunningAverageRssiFilter.setSampleExpirationMilliseconds(5000l);
            Beacon.setHardwareEqualityEnforced(true);
        } catch (RemoteException e) {    }
    }
}


