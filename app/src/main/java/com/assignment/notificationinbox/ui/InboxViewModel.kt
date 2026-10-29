package com.assignment.notificationinbox.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.assignment.notificationinbox.data.NotificationEntity
import com.assignment.notificationinbox.data.NotificationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InboxViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = NotificationRepository.from(app)

    val items: StateFlow<List<NotificationEntity>> =
        repo.notifications.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}

class DetailViewModel(app: Application, handle: SavedStateHandle) : AndroidViewModel(app) {
    private val repo = NotificationRepository.from(app)
    private val id: String = handle.get<String>("id").orEmpty()

    val item: StateFlow<NotificationEntity?> =
        repo.notification(id).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    init {
        // Opening the details screen marks the notification as read.
        viewModelScope.launch { repo.markRead(id) }
    }
}
