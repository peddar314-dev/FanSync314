# GitHub APK build

The workflow is .github/workflows/build-apk.yml. The Gradle settings, build file, wrapper and app directory must remain at repository root.

Open Actions, choose Build FanSync Android APK, and run it on main. Pushes and pull requests also run automatically. A successful run contains FanSync-Demo-APK and FanSync-Reports artifacts. Download and unzip the APK artifact.

If a run fails, inspect Build and test debug APK and download FanSync-Reports when available. The workflow checks compilation, unit tests and Android lint, so warnings about action runtimes are not a substitute for the failing step's actual error.
