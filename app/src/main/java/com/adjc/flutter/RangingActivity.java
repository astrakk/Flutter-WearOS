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
                long[] vibrationPattern = {0, 500, 50, 300};

                // Iterate over all beacons, logging their distances
                for (Beacon beacon: beacons) {
                    Log.println(Log.INFO, TAG, "Distance is " + beacon.getDistance() * 10.0);

                    // If beacon is within 0.5 metres, vibrate and display on screen
                    if (beacon.getDistance() * 10.0 < 0.5) {
                        nearbyBeacons++;
                        textView_beacons.setText("Beacons in range: " + nearbyBeacons);
                        vibrator.vibrate(vibrationPattern, -1);
                    }
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            // Reduce the sample size from 20s to 5s. Reduces accuracy but improves speed.
            BeaconManager.setRssiFilterImplClass(RunningAverageRssiFilter.class);
            RunningAverageRssiFilter.setSampleExpirationMilliseconds(5000l);
        } catch (RemoteException e) {    }
    }
}


