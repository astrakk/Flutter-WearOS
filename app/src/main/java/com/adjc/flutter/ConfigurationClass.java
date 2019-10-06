package com.adjc.flutter;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationClass {
    private int beacon0VibrationId;
    private int beacon1VibrationId;

    private String beacon0Id;
    private String beacon1Id;

    // Define the vibration patterns list
    private List<long[]> vibrationPatterns = new ArrayList<long[]>();

    private long[] pattern0 = {0, 500, 50, 300};
    private long[] pattern1 = {0, 200, 25, 200, 25, 500};
    private long[] pattern2 = {0, 600, 20, 100};
    private long[] pattern3 = {0, 150, 50, 150, 50, 150, 300};
    private long[] pattern4 = {0, 600, 50, 600, 50, 100};

    // Constructor
    public ConfigurationClass() {
        beacon0VibrationId = 0;
        beacon1VibrationId = 1;

        beacon0Id = "CE:6B:A4:98:69:9D";
        beacon1Id = "C1:23:0E:0C:0E:0D";

        vibrationPatterns.add(pattern0);
        vibrationPatterns.add(pattern1);
        vibrationPatterns.add(pattern2);
        vibrationPatterns.add(pattern3);
        vibrationPatterns.add(pattern4);
    }

    public List<long[]> getVibrationPatterns() {
        return vibrationPatterns;
    }

    public int getVibrationPatternCount() {
        return vibrationPatterns.size();
    }

    public long[] getVibrationPattern(int vibrationId) {
        return vibrationPatterns.get(vibrationId);
    }

    public List<String> getVibrationPatternsArrayPretty(){
        List<String> prettyArray = new ArrayList<>();

        for (int i = 0; i < getVibrationPatternCount(); i++) {
            prettyArray.add("Vibration #" + i);
        }

        return prettyArray;
    }


    public String getBeacon0Id() {
        return beacon0Id;
    }

    public void setBeacon0VibrationId(int vibrationId) {
        beacon0VibrationId =  vibrationId;
    }

    public int getBeacon0VibrationId() {
        return beacon0VibrationId;
    }

    public long[] getBeacon0VibrationPattern() {
        return vibrationPatterns.get(beacon0VibrationId);
    }


    public String getBeacon1Id() {
        return beacon1Id;
    }

    public void setBeacon1VibrationId(int vibrationId) {
        beacon1VibrationId =  vibrationId;
    }

    public int getBeacon1VibrationId() {
        return beacon1VibrationId;
    }

    public long[] getBeacon1VibrationPattern() {
        return vibrationPatterns.get(beacon1VibrationId);
    }
}
