package bladehurts.ticketbuyer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val statusText = TextView(this).apply {
            text = getStatusMessage()
            textSize = 18f
            setPadding(20, 40, 20, 40)
        }

        val notificationBtn = Button(this).apply {
            text = "Grant Notification Access"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
            }
        }

        val accessibilityBtn = Button(this).apply {
            text = "Grant Accessibility Access"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }

        val refreshBtn = Button(this).apply {
            text = "Refresh Permissions Status"
            setOnClickListener {
                statusText.text = getStatusMessage()
            }
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 100, 40, 100)
            addView(statusText)
            addView(notificationBtn)
            addView(accessibilityBtn)
            addView(refreshBtn)
        }

        setContentView(layout)
    }

    private fun getStatusMessage(): String {
        val notifGranted = isNotificationServiceEnabled()
        val accessGranted = isAccessibilityServiceEnabled()
        return "Notification Access: ${if (notifGranted) "✅" else "❌"}\n" +
                "Accessibility Access: ${if (accessGranted) "✅" else "❌"}"
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return flat?.contains(pkgName) == true
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val expectedService = ComponentName(this, MyAccessibilityService::class.java).flattenToString()
        val enabledServices = Settings.Secure.getString(contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
        Log.d(TAG, "enabledServices")
        Log.d(TAG, enabledServices)
        return enabledServices
            ?.split(":")
            ?.any { it.equals(expectedService, ignoreCase = true) } == true
    }

}