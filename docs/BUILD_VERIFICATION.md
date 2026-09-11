# FanSync build verification — 2026-09-11

Inspected origin peddar314-dev/FanSync314 at 82fe57ae589dc0542d4cb3fb07a7fc41224cc7d1.

## Diagnosis and repair

The workflow ran Gradle at repository root while settings.gradle, build.gradle and app were nested in FanSync_GitHub_Android. Its APK upload path had the same mismatch. GitHub run 33915223434 shows successful setup followed by a failed Build debug APK step. Public annotations expose only exit code 1, not the complete Gradle log, so the historical log was not available for line-by-line verification.

Moved the Android project to root, generated a Gradle 8.9 wrapper with official distribution checksum and executable Unix script, retained compatible AGP 8.7.3/JDK 17/API 35, and explicitly pinned build-tools 35.0.0. Workflow uses checkout v6, setup-java v5, setup-android v4, setup-gradle v6/basic cache, and upload-artifact v7. It checks pull requests and main pushes, limits permissions and runtime, and saves reports.

Fixed callback-count-based countdown drift, repeated-trigger text size, timer lifecycle, rotation state and stale resume vibration. Added system-bar insets, bounded custom text, autosizing, string resources and a launcher icon. Added the independent CheerSession model and a documented realtime event/timing contract. The app remains a local demo.

## Verified locally

- Clean wrapper command: gradlew.bat clean :app:assembleDebug :app:testDebugUnitTest :app:lintDebug --no-daemon --stacktrace
- BUILD SUCCESSFUL, 48 tasks executed.
- Five unit tests passed, zero failures or skipped tests.
- Android lint: zero issues.
- APK signature verifies (v2), one development signer.
- APK alignment check passes.
- Application ID com.fansync.demo; minimum Android 8.0/API 26; target API 35.

The compiler notes use of deprecated compatibility APIs for system insets and vibration; these remain available on the supported Android versions. An earlier overlapping local build hit a Windows file lock; the final single clean wrapper build above passed.

## Remaining verification

No physical-device or emulator install/launch test has been performed. Actual haptic feel and rotation/UI behavior need the phone checklist in README.md. No realtime server is connected. GitHub has not received these local changes or run the corrected workflow yet because Git has no signed-in GitHub account on this machine. The source archive and patch preserve the complete changes for publication after authentication.
