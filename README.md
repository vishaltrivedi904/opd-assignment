# KhushiBaby - OPD Management System - MVVM

This is a scalable, offline-supported Android application developed for KhushiBaby using Kotlin, Jetpack Compose, Room for offline data persistence, Hilt for Dependency Injection, and MVVM architecture. The app implements a minimal OPD management flow, including patient registration, visit details, and prescription summary screens. The application is designed to work seamlessly in areas with limited or no internet connectivity by storing data locally and syncing it when network access is restored.

## Features
- **Patient Registration Screen**: Allows users to input patient details and store them locally.
- **Patient Visit Details Screen**: Captures visit details and prescription information, with offline-first data handling.
- **Prescription Summary Screen**: Displays patient visit details and prescriptions with options to print/share (mock actions).
- **Offline Data Persistence**: Utilizes **Room** for local data storage to handle offline scenarios.
- **Synchronization with Server**: Syncs data with a mock API endpoint when internet connectivity is restored using **WorkManager**.

## Tech Stack
- **Kotlin**: Primary language for app development.
- **Jetpack Compose**: Used for UI implementation with modern, declarative syntax.
- **Room**: Used for offline data persistence in the local database.
- **Coroutines**: For asynchronous operations and background tasks.
- **MVVM (Model-View-ViewModel)**: Clean architecture to separate concerns and maintain testable code.
- **Hilt**: Dependency Injection framework for managing app dependencies.
- **WorkManager**: Used to manage background synchronization tasks when network connectivity is available.
- **Material Design**: UI components follow Material Design principles to ensure a consistent and intuitive user experience.
- **Version Catalogs (Gradle)**: Used for managing dependencies.

## Architecture
The app follows the **MVVM (Model-View-ViewModel)** architecture to ensure a clean separation of concerns:
- **Model**: Represents the data layer, which includes **Room** for local storage and network requests.
- **View**: UI layer composed using **Jetpack Compose**.
- **ViewModel**: Handles business logic and updates the View through **LiveData**.

### Room Database
- **Room** is used for offline data persistence. It stores patient registration details, visit details, and prescription information.
- It ensures offline data storage and provides a clean abstraction for database operations using **Kotlin Coroutines** for asynchronous operations.

### Hilt for Dependency Injection
- **Hilt** is used to inject dependencies across the app. It simplifies the management of dependencies and avoids boilerplate code for DI.
- **Hilt** is integrated for injecting instances of the **ViewModel**, **Repository**, and **Room Database** objects.
- Hilt allows us to define dependencies in a clear, modular way and ensures the proper lifecycle management of components.

### Coroutines
- **Coroutines** are used for all asynchronous tasks such as database operations, network calls, and background syncing.
- Room's database operations (e.g., insert, update, delete) are performed in the background using **Coroutines** to prevent blocking the main thread.

### WorkManager
- **WorkManager** is used to manage background synchronization tasks to upload offline data to the server when the device is online.
- WorkManager ensures that the sync process is reliable and efficient, even if the app is in the background or the device goes to sleep.

## Features Implementation

### 1. Patient Registration Screen
- Input fields: Name, Age, Gender, Contact Number (optional), Location, and Health ID.
- Data validation for required fields (e.g., Name and Age).
- Saves data locally in **Room** database using **Coroutines**.
- **Hilt** is used to inject the **Repository** and **ViewModel** for handling the data.
- UI is created using **Jetpack Compose**.

### 2. Patient Visit Details Screen
- Input fields: Visit Date (pre-filled with today's date), Symptoms, Diagnosis, Medicine Name, Dosage, Frequency, and Duration.
- Saves visit details locally in **Room** and syncs with the server when online.
- **Coroutines** are used to handle asynchronous database operations.
- **Hilt** is used for dependency injection to provide the **ViewModel** and **Repository**.
- UI is built using **Jetpack Compose** for an intuitive user experience.

### 3. Prescription Summary Screen
- Displays a summary of patient details, visit information, and prescriptions.
- Allows the option to mark the visit as "Completed" and saves the status locally in **Room**.
- Includes mock actions to share via WhatsApp/SMS and print the prescription.

## Offline Support & Synchronization

- **Offline-first strategy**: Data is saved locally on the device using **Room**.
- **Sync Logic using WorkManager**: 
  - The app uses **WorkManager** to manage background synchronization tasks, especially for syncing offline data to the server when the device is online.
  - **WorkManager** checks for internet availability and synchronizes data with the server when connectivity is restored.
  - The data sync happens in the background using **Coroutines** for smooth operations without blocking the main thread.

### How Synchronization Works with WorkManager:
1. User enters data on the device (e.g., Patient Registration, Visit Details).
2. Data is saved locally in the **Room** database.
3. **WorkManager** is used to schedule background tasks to check for network availability and attempt to sync locally stored data with the server when the internet connection is available.
4. If the user is offline, the data remains on the device and syncs automatically when the device reconnects to the internet.
5. The app uses **Coroutines** to perform the network sync in the background without freezing the UI.

## App Flow

The app follows a specific user flow for navigating through the screens:

1. **Splash Screen**: Initially, the app displays a splash screen.
2. **Patient List Screen**: Once the splash screen finishes, the app opens the **Patient List Screen**.
   - The screen will display a list of all registered patients.
   - A **"Register Patient"** button is available to go to the **Patient Registration Screen**.
3. **Patient Registration Screen**:
   - The user can input patient details such as **Name**, **Age**, **Gender**, **Location**, and **Health ID**.
   - After entering the required data, the user can save the details locally in the database.
   - On successful save, the user is redirected back to the **Patient List Screen**.
4. **Visit Details Screen**:
   - In the **Patient List Screen**, when a patient is selected, the app navigates to the **Patient Visit Details Screen**.
   - This screen allows the user to input **Visit Date**, **Symptoms**, **Diagnosis**, **Medicine Name**, **Dosage**, **Frequency**, and **Duration**.
   - The visit details are saved locally in the database and can be synced with the server later when online.
5. **Prescription Summary Screen**:
   - After saving the visit details, the user can view the **Prescription Summary Screen**.
   - This screen displays patient details, visit information, and prescriptions.
   - The user can **Mark Visit as "Completed"**, which updates the status and saves it locally.
   - Mock actions for **Printing** and **Sharing** prescriptions are available but not implemented.
6. **Sync Logic**:
   - If the device is online, the app syncs the local data with the server.
   - If the device is offline, the data is saved locally and synchronized later when the device regains connectivity using **WorkManager**.
   
## How to Run the App Locally

### Prerequisites:
Before running the app, make sure you have the following:

- **Android Studio** (latest stable version)
- **Kotlin** (installed with Android Studio)
- **Android Emulator** or a **physical device** for testing
- **Git** installed on your machine to clone the repository

### Steps:
1. **Clone the repository**:
   First, clone the repository to your local machine:
   ```bash
   git clone https://github.com/vishaltrivedi904/opd-assignment.git
