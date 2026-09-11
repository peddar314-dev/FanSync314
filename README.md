# FanSync Android Demo

Away-game cheering demo for Android 8.0 and newer. The operator enters a custom cheer and triggers a five-second countdown on this phone, followed by CHEER NOW and a vibration cue. Reset cancels the countdown. No account or network is needed.

## Build

Open this repository root in Android Studio, or install JDK 17 and Android SDK platform 35/build-tools 35.0.0, then run:

```sh
./gradlew :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

On Windows use `gradlew.bat`. Set ANDROID_HOME or an untracked local.properties with sdk.dir. The checked-in wrapper pins Gradle 8.9, compatible with Android Gradle Plugin 8.7.3. The output is app/build/outputs/apk/debug/app-debug.apk, signed automatically with a development key.

GitHub Actions runs the same checks on pushes to main, pull requests, and manual dispatch. Download FanSync-Demo-APK from a successful run, unzip it, and open the APK on your Android phone. Allow installation from the app used to open it if Android asks. This development build is for sideloaded demos; store distribution needs a managed release signing key.

## Behavior checks on a phone

1. Start a cheer: 5, 4, 3, 2, 1, then CHEER NOW with the entered text and a short vibration if supported.
2. Trigger again: large countdown digits return.
3. Reset midway: JOINED returns and no delayed cue occurs.
4. Clear the message: GO TEAM! is used.
5. Rotate during countdown: the deadline and message are preserved.
6. Background the app and return more than a second after its deadline: the result appears without a stale vibration.
7. Check large font, landscape, and keyboard layouts. Cheer text is limited to 80 characters.

## Structure and next phase

The Android app is at the repository root, alongside the workflow and Gradle wrapper. CheerSession is a tested, Android-independent timing model. MainActivity handles only local triggering, display, lifecycle, and vibration. See docs/REALTIME_PLAN.md for the multi-phone transport and timing contract. Realtime networking is not implemented in this demo.
