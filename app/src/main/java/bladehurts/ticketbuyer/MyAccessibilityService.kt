package bladehurts.ticketbuyer

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.util.Log

class MyAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        Log.d("TicketBot", "Accessibility event: ${event.eventType}, class: ${event.className}")

        val rootNode = rootInActiveWindow
        if (rootNode == null) {
            Log.w("TicketBot", "rootInActiveWindow is null.")
            return
        }

        Log.d("TicketBot", "rootNode.className = ${rootNode.className}")
        Log.d("TicketBot", "Initial UI tree dump:")
        logNodeTree(rootNode, 0)

        Handler(Looper.getMainLooper()).postDelayed({
            val delayedRoot = rootInActiveWindow
            if (delayedRoot == null) {
                Log.w("TicketBot", "Delayed rootInActiveWindow is null.")
                return@postDelayed
            }

            Log.d("TicketBot", "Delayed UI tree dump:")
            logNodeTree(delayedRoot, 0)

            val labelNode = findNodeByText(delayedRoot, "Add to cart")
            if (labelNode != null) {
                val clickableParent = findClickableParent(labelNode)
                if (clickableParent != null) {
                    clickableParent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    Log.d("TicketBot", "Clicked parent of 'Add to cart' TextView")
                } else {
                    Log.w("TicketBot", "No clickable parent found for 'Add to cart'")
                }
            } else {
                Log.w("TicketBot", "TextView with 'Add to cart' not found")
            }
        }, 600)
    }

    private fun logNodeTree(node: AccessibilityNodeInfo?, depth: Int) {
        if (node == null) return

        val indent = "  ".repeat(depth)
        val info = "$indent class=${node.className}, text=${node.text}, contentDesc=${node.contentDescription}, clickable=${node.isClickable}, id=${node.viewIdResourceName}"
        Log.d("TicketBot", info)

        for (i in 0 until node.childCount) {
            logNodeTree(node.getChild(i), depth + 1)
        }
    }

    private fun findNodeByText(node: AccessibilityNodeInfo?, text: String): AccessibilityNodeInfo? {
        if (node == null) return null
        if (node.text?.toString()?.equals(text, ignoreCase = true) == true) {
            return node
        }
        for (i in 0 until node.childCount) {
            val result = findNodeByText(node.getChild(i), text)
            if (result != null) return result
        }
        return null
    }

    private fun findClickableParent(node: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        var current = node
        while (current != null) {
            if (current.isClickable) return current
            current = current.parent
        }
        return null
    }

    override fun onInterrupt() {
        Log.d("TicketBot", "AccessibilityService interrupted")
    }
}