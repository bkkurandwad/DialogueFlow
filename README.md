# NLP HRE - Android App (Frontend)

## Overview
NLP HRE is an Android application designed to automate HR-related work updates and enhance communication between HR and employees. The app allows employees to receive notifications and automated voice calls with their work updates. HR personnel can update work details using a custom-made server that integrates with Dialogflow, Firebase Cloud Messaging (FCM), and Google Text-to-Speech (TTS).

This repository contains the Android app for the NLP HRE project, where employees can log in, receive notifications, and listen to their work updates via automated voice calls.

## Features
- Employee Login: Secure login interface for employees.
- HR Notifications: Real-time notifications powered by Google Firebase Cloud Messaging (FCM) when there is a new work update.
- Automated Voice Call: When an employee receives a notification, they can receive a simulated voice call that reads out the work update details.
- Work Update Interaction: After receiving a notification, the employee can answer or cut the call. The server tracks this interaction to update the status.

## Architecture
- **Frontend (Mobile App)**: The user interface where employees interact with the system.
- **Backend Server**: The backend, not covered in this repository, handles the work updates, notifications, and voice call generation. It integrates with Dialogflow and communicates with Google TTS and FCM.
- **Dialogflow**: Handles the processing of HR's work updates through a conversational interface.
- **Firebase Cloud Messaging (FCM)**: Used for sending real-time notifications to employees about work updates.
- **Google Text-to-Speech (TTS)**: Converts work update details into speech (MP3) and sends them to the employee’s mobile app.

### Flow of Operations:
1. HR updates work details through the backend.
2. The backend triggers an FCM notification to the employee’s mobile app.
3. Upon receiving the notification, the employee answers or cuts the call.
4. If the call is answered, the app will play the audio with your work update details.

## Setup

### Backend Repo
```bash
   git clone https://github.com/bkkurandwad/webhook.git
   ```

### Prerequisites
- Android Studio installed.
- Firebase project setup for FCM and Text-to-Speech integration.
- Google Cloud account for using TTS API (handled on the backend).

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/bkkurandwad/DialogueFlow.git
   ```
