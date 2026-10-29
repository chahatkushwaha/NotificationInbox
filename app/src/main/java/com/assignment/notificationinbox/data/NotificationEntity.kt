package com.assignment.notificationinbox.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Primary key = notification id, so the same id can never create two rows. */
@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val bannerImg: String?,
    val timestamp: Long,
    val isRead: Boolean = false
) {
    companion object {
        /**
         * Builds an entity from an FCM data map. Tolerates missing/invalid fields.
         * Returns null only if there is nothing meaningful to show.
         */
        fun fromData(data: Map<String, String>): NotificationEntity? {
            val title = data["title"]?.trim().orEmpty()
            val description = data["description"]?.trim().orEmpty()
            if (title.isEmpty() && description.isEmpty()) return null

            val timestamp = data["timestamp"]?.trim()?.toLongOrNull() ?: System.currentTimeMillis()
            val id = data["id"]?.trim()?.takeIf { it.isNotEmpty() }
                ?: "generated_${(title + description + timestamp).hashCode()}"

            return NotificationEntity(
                id = id,
                title = title.ifEmpty { "Notification" },
                description = description,
                bannerImg = data["bannerImg"]?.trim()?.takeIf { it.isNotEmpty() },
                timestamp = timestamp
            )
        }
    }
}
