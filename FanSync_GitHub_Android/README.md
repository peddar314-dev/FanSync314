# FanSync Android Demo

A self-contained Android prototype for the FanSync away-game synchronized cheering concept.

## Build
This repository includes a GitHub Actions workflow at:

`.github/workflows/build-apk.yml`

It builds a debug APK and publishes it as the artifact:

`FanSync-Demo-APK`

See `GITHUB_BUILD_GUIDE.md` for the click-by-click process.

## Current demo features
- FanSync branded demo screen
- Custom cheer entry
- 5-second countdown
- CHEER NOW state
- Haptic/vibration cue
- Reset/retest

## Next iteration
- Realtime multi-phone synchronization
- Fan/team authentication
- Game sessions
- Operator authorization
- Presence counts
- Timing telemetry
