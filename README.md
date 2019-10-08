![header](https://i.imgur.com/6jKXCXR.png)

## Requirements
 - WearOS device
 - iBeacons

## Installation
To begin using Flutter in your setup, you'll need to make some adjustments to the code (I know, I know). You'll also need to have Android Studio installed on your system to apply the changes to the WearOS device of your choosing.
 1. Under `/app/src/main/java/com/adjc/flutter/ConfigurationClass.java` you'll need to change `Beacon0Id` and `Beacon1Id` to the bluetooth hardware IDs of your beacons.
 2. Build the project using Android Studio
 3. Push the project to your WearOS device
 
## How to use
Flutter has been built to be as simple as possible by design. To begin, simply open the app and tap *Start*. This will kick off the *Ranging* process and will vibrate according to your preferences under the *Settings* page.

To ensure the students cannot turn off the feature accidentally, you'll need to either end the Flutter process via Android Studio/ADB or reboot the device via the settings app.
