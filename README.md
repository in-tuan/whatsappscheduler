# WhatsApp Scheduler
![Platform](https://img.shields.io/badge/platform-Android-green) ![Status](https://img.shields.io/badge/status-Alpha-orange)

Schedule WhatsApp messages to be sent at the perfect time.
Because remembering to message people is just too much work.

<img src="/assets/app_page" alt="App Screenshot" width="250"/>

# Requirements
- Java 11 or higher (JDK 11+)
- Android SDK 36 or higher
- Gradle (or use included `gradlew` wrapper)
- Git
- Android Studio (recommended)

# Get Started
## To run the app locally:
### Android Studio
1. **Clone this repository.** If you would like to contribute, fork the repo first and then clone your fork:
    ```git clone https://github.com/in-tuan/whatsappscheduler.git```
2. Open the project in Android Studio.
   - Launch Android Studio
   - Select File &rarr; New &rarr; Project from existing sources.
3. Run the app on device or emulator.
   - Connect a device via USB or Wi-Fi, or start an emulator
   - Press the Run ▶️ button in Android Studio
4. Grant permissions
    - To ensure functional usage of apps, grant all requested permissions on first launch.
### Command Line
1. Clone this repository.
   
    ```git clone https://github.com/in-tuan/whatsappscheduler.git```
2. Locate and go into the repository.

    ```cd whatsappscheduler```
3. Build the apk.

    ```./gradlew assembleDebug```
4. Connect a device via USB or Wi-Fi (adb)
5. Install the APK on a connected device

    ```./gradlew installDebug```

# Download the app:
Possible addition: A direct APK download link will be available here.
(May also include a QR code for quick installation.)

# Permissions

This app requires the following permissions:

- **Notification Access**: To schedule and send WhatsApp message reminders.
- **Alarm Permission**: To trigger scheduled messages at the specified time.

Permissions will be requested when you first use the app to ensure it works as intended.


