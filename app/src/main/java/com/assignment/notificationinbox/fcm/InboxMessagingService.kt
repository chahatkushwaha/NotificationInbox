package com.assignment.notificationinbox.fcm

import android.util.Log
import com.assignment.notificationinbox.data.NotificationRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.runBlocking

class InboxMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        try {
            val data = message.data.toMutableMap()
            // Fall back to the "notification" block if title/description are not in data.
            message.notification?.let {
                if (data["title"].isNullOrBlank()) it.title?.let { t -> data["title"] = t }
                if (data["description"].isNullOrBlank()) it.body?.let { b -> data["description"] = b }
            }
            val saved = runBlocking { NotificationRepository.from(applicationContext).saveFromData(data) }
            saved?.let { NotificationHelper.show(applicationContext, it) }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to handle message", e)
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "FCM token: $token")
    }

    private companion object { const val TAG = "InboxMessaging" }
}
