# Firebase Notification Inbox (Android, Kotlin + Compose)

Stack: Kotlin, Jetpack Compose, Room, Coil, Navigation Compose, Firebase Cloud Messaging.

## Setup
1. Create a Firebase project and add an Android app with package `com.assignment.notificationinbox`.
2. Download `google-services.json` and place it in `app/`.
3. Open in Android Studio, sync, run on a device/emulator with Google Play services.

## Payload (FCM data message, all values are strings)
id, title, description, bannerImg, timestamp

## Design notes
- `id` is the Room primary key; inserts use IGNORE, so duplicates never create rows or reset read state.
- Missing id -> generated hash; missing timestamp -> now; missing/invalid banner -> placeholder.
- Channel `assignment` / `Assignment` is created in `App`.
- System notification tap -> MainActivity (Inbox). Inbox item tap -> Details (marks read).
- Background "notification" messages (e.g. from Firebase Console) are captured from intent extras.
