package com.assignment.notificationinbox.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class NotificationRepository(private val dao: NotificationDao) {

    val notifications: Flow<List<NotificationEntity>> = dao.observeAll()

    fun notification(id: String): Flow<NotificationEntity?> = dao.observeById(id)

    /** @return the parsed entity, or null if the payload was unusable. Duplicates are silently ignored. */
    suspend fun saveFromData(data: Map<String, String>): NotificationEntity? {
        val entity = NotificationEntity.fromData(data) ?: return null
        dao.insert(entity)
        return entity
    }

    suspend fun markRead(id: String) = dao.markRead(id)

    companion object {
        fun from(context: Context) = NotificationRepository(AppDatabase.get(context).notificationDao())
    }
}
