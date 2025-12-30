# UPDATE: The app no longer works—TicketSwap has released a patch. I may release a new solution soon, but no promises.

# TicketSwapBot 🕵️🎟️

TicketSwapBot is a lightweight Android automation tool built to automatically react to ticket availability notifications from the TicketSwap app. Its goal is to minimize delay between ticket drop alerts and user actions like opening the app and tapping "Add to cart".

![Demo](demo.gif)

## 🚀 Features

- 📬 Listens for push notifications from the TicketSwap app
- 🚀 Automatically opens the app when relevant notifications arrive
- 👀 Uses AccessibilityService to find and click the "Add to cart" button
- 💤 Keeps the screen awake to ensure the device doesn't sleep during critical periods

## 🛠 How It Works

1. **NotificationListenerService** catches notifications from TicketSwap.
2. **PendingIntent** from the notification is used to open the TicketSwap app.
3. **AccessibilityService** waits for the ticket listing screen and clicks the appropriate purchase button.

## ⚙️ Setup Instructions

1. Clone this repo.
2. Open in Android Studio.
3. Install the app on your device (real device, since TicketSwap might not run correctly on an emulator).
4. Optionally, customize supported strings in different languages in the source code.

## 📥 How to Install Directly
You can skip building from source by downloading the latest APK from the [Releases page](https://github.com/balazsbanto/ticket-buyer-bot/releases).
Once downloaded, install it on your Android device and manually grant the required permissions in system settings.

## 🔐 Permissions

This app requires the following permissions to function:
- `BIND_NOTIFICATION_LISTENER_SERVICE`
- `BIND_ACCESSIBILITY_SERVICE`

These are granted manually by the user in system settings.

##  ⚠️ Limitations & Room for Improvement
🌐 The app currently only works with the English language interface, as it identifies the "Add to cart" button by matching its text.

🎯 It will attempt to add all tickets for which you receive notifications. This can be refined in the NotificationListenerService by filtering based on specific event titles or other attributes.

📱 TicketSwap does not work reliably on emulators — it may use emulator detection. For best results, use a real device, or explore options to bypass emulator detection.

## 🤖 Intended Use

This project is intended for **educational and personal use**. It demonstrates how Android's accessibility and notification systems can be used to automate workflows for UI-driven apps.

⚠️ **Note:** Automating commercial apps may violate their terms of service. Use responsibly.

## 📄 License

MIT License. See [LICENSE](LICENSE) for details.

