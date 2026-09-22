# J530Y LTE Tools

Small Android utility for Samsung Galaxy J5 Pro SM-J530Y.

## Purpose

Opens Samsung's internal Phone Information / Testing menu:

com.android.settings/.Settings$TestingSettingsActivity

Root access is required.

## Build

The project is designed for GitHub Actions.

1. Upload the project to GitHub.
2. Open Actions.
3. Select "Build J530Y LTE Tools".
4. Click "Run workflow".
5. Download the generated artifact.
6. Extract `app-debug.apk`.
7. Install it on the rooted J530Y.

## LTE Only

Open:

Phone Information
â†’ Set preferred network type
â†’ LTE only

The application does not directly modify the modem or preferred network mode.

## Target

Samsung Galaxy J5 Pro
SM-J530Y
Android 9
