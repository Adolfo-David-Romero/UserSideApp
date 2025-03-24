🧑‍🦼 UserSideApp
=================

**UserSideApp** is a prototype Android app designed for individuals who are being cared for. It works together with the **Caregiver** app to create a remote health monitoring system. This app collects and sends health or device data (e.g. vitals, movements, camera input, etc.) to a caregiver's app using **Firebase Cloud**.

The app is meant to be easy to use for care receivers (such as elderly people or patients), while all monitoring and alerts are handled by the connected **Caregiver** app.

* * * * *

🌐 How It Works
---------------

1.  The care receiver (user) runs this app on their Android phone.

2.  The app connects to Firebase and sends data (e.g., vital signs, camera feed, etc.).

3.  The caregiver (on another device) runs the **Caregiver App** to view and monitor the user's status in real-time.

* * * * *

🧩 System Requirements
----------------------

-   Android Studio (Electric Eel or newer recommended)

-   Android SDK version **33+**

-   Java JDK **11 or newer**

-   A Firebase project (shared with the Caregiver app)

-   Android device running Android **10 (API 29)** or later

* * * * *

🛠️ How to Set Up and Run the App (Step-by-Step)
------------------------------------------------

This guide will help you get the app running using Android Studio. No coding experience required!

### 1\. 📥 Install Android Studio

-   Download and install [Android Studio](https://developer.android.com/studio)

-   During the first launch, allow it to install SDK tools and components

### 2\. 📁 Get the Project Files

#### Option A: Clone with Git

```
git clone https://github.com/Adolfo-David-Romero/UserSideApp.git
```

#### Option B: Download ZIP

-   Click the green **Code** button on the GitHub page

-   Select **Download ZIP**

-   Extract the ZIP folder to a known location

### 3\. 📂 Open the Project in Android Studio

-   Open Android Studio

-   Click **Open an Existing Project**

-   Navigate to the extracted **UserSideApp** folder and open it

Wait for Gradle to finish syncing (this might take a few minutes on the first run).

* * * * *

🔥 Connect to Firebase
----------------------

This app uses Firebase to sync with the Caregiver App.

### A. Set Up Firebase Project (if not already done)

If you haven't already created a Firebase project:

1.  Go to Firebase Console

2.  Click **Add project**

3.  Follow the instructions to create a new project

> ✅ **Important**: This should be the **same Firebase project** used by the **Caregiver** app.

### B. Register Android App with Firebase

1.  Inside Firebase Console, click **Add App > Android**

2.  Enter your app's package name (you can find it in `build.gradle (Module: app)`)

3.  Download the `google-services.json` file after registration

4.  Place it into the `app/` directory of the UserSideApp project

### C. Enable Firebase Services

-   Go to **Firestore Database** in the Firebase console and create a database (select test mode for now)

-   Enable any other services the app uses, such as Realtime Database, Storage, or Authentication

* * * * *

▶️ Run the App
--------------

-   Connect your Android device or use an emulator

-   Click the green **Run** button in Android Studio

-   The app will launch on the selected device

Once the app is running and connected to Firebase, it will begin syncing data to the cloud. The caregiver should now be able to view updates from the **Caregiver App**.

* * * * *

🔄 Features
-----------

-   Syncs data from care receiver to caregiver via Firebase

-   Can send health info, movements, and/or camera-based data

-   Simple UI for minimal user interaction

-   Designed for use by patients, elderly, or non-technical users

* * * * *

🧑‍⚕️ Paired App: Caregiver
---------------------------

This app works best when paired with the **Caregiver App** (run by the person monitoring the user). You can find it here:

👉 [Caregiver App GitHub Repo](https://github.com/Adolfo-David-Romero/Caregiver)

* * * * *

🛠 Tech Stack
-------------

-   **Language**: Kotlin

-   **Architecture**: MVVM

-   **Backend**: Firebase (Firestore + Realtime DB)

-   **IDE**: Android Studio

* * * * *

📬 Contact
----------

Have questions or want to collaborate?

-   GitHub: [Adolfo-David-Romero](https://github.com/Adolfo-David-Romero)
