# FanSync — GitHub Build Instructions

This package is designed so GitHub Actions can build the Android APK for you.

## Step 1 — Create a GitHub account
If you do not already have one:
https://github.com/signup

## Step 2 — Create a new repository
Open:
https://github.com/new

Recommended settings:
- Repository name: `FanSync-Android`
- Visibility: Private
- Do NOT add a README, .gitignore, or license when creating the repository.

Click **Create repository**.

## Step 3 — Upload the project files
Unzip `FanSync_GitHub_Android.zip` on a computer.

Inside your new GitHub repository:
1. Click **Add file**.
2. Click **Upload files**.
3. Drag the CONTENTS of the unzipped `FanSync_GitHub_Android` folder into GitHub.
4. Make sure the `.github` folder is included.
5. Click **Commit changes**.

GitHub's upload instructions:
https://docs.github.com/en/repositories/working-with-files/managing-files/adding-a-file-to-a-repository

## Step 4 — Build the APK
Open the repository and click the **Actions** tab.

You should see:
`Build FanSync Android APK`

Because the workflow also runs on pushes to `main`, the first build may already be running after your upload.

If needed:
1. Click **Build FanSync Android APK**.
2. Click **Run workflow**.
3. Click the green **Run workflow** button.

## Step 5 — Download the APK
When the workflow finishes successfully:
1. Open the completed workflow run.
2. Scroll to **Artifacts**.
3. Click **FanSync-Demo-APK**.
4. GitHub downloads a ZIP file.
5. Extract it.
6. Inside is `app-debug.apk`.

GitHub explains workflow artifacts here:
https://docs.github.com/en/actions/concepts/workflows-and-actions/workflow-artifacts

## Step 6 — Install on Android
Move `app-debug.apk` to your Android phone, if it is not already there.

Tap the APK. Android may ask you to allow installation from the browser/files app you used. Approve that permission for this test install, then install FanSync.

For security, you can turn that permission back off after installation.

## What this APK does
This first test build is intentionally simple and offline:
- Opens as **FanSync Demo**
- Shows an away-game screen
- Lets you type a cheer
- Triggers a 5-second countdown
- Displays **CHEER NOW!**
- Vibrates the phone

It does NOT yet synchronize multiple phones over the internet. The next build can add Supabase/Firebase realtime synchronization after this user experience is approved.

## If the GitHub build fails
Open the failed workflow run and copy the red error text into ChatGPT. The project can then be corrected based on the actual build log.
