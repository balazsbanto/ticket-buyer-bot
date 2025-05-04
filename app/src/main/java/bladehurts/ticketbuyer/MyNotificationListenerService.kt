package bladehurts.ticketbuyer

import android.app.Notification
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log


class MyNotificationListenerService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName == "com.ticketswap.ticketswap") {
            val notification = sbn.notification ?: return

            val extras = notification.extras
            val packageName = sbn.packageName
            val title = extras.getString(Notification.EXTRA_TITLE)?:""
            val text = extras.getCharSequence(Notification.EXTRA_TEXT)
            val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)

            Log.d(TAG, "Package: $packageName")
            Log.d(TAG, "Title: $title")
            Log.d(TAG, "Text: $text")
            Log.d(TAG, "BigText: $bigText")

            try {
                // Can't open app just through the notification because of BAL restrictions
                // Error message: Without BAL hardening this activity start would be allowed!
                sbn.notification.contentIntent?.send()
                Log.d(TAG, "Notification clicked via PendingIntent")

                // Force-launch the TicketSwap app after a short delay
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = packageManager.getLaunchIntentForPackage("com.ticketswap.ticketswap")
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        Log.d(TAG, "TicketSwap launched manually after PendingIntent")
                    } else {
                        Log.e(TAG, "Could not get launch intent for TicketSwap")
                    }
                }, 500)

            } catch (e: Exception) {
                Log.e(TAG, "Error clicking notification", e)
            }

//            }
        }
    }
}