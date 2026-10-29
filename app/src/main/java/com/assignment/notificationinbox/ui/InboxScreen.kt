package com.assignment.notificationinbox.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.assignment.notificationinbox.data.NotificationEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(onOpen: (String) -> Unit, vm: InboxViewModel = viewModel()) {
    val items by vm.items.collectAsStateWithLifecycle()
    Scaffold(topBar = { TopAppBar(title = { Text("Notification Inbox") }) }) { padding ->
        if (items.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) { Text("No notifications yet") }
        } else {
            LazyColumn(
                Modifier.padding(padding),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items, key = { it.id }) { InboxItem(it) { onOpen(it.id) } }
            }
        }
    }
}

@Composable
private fun InboxItem(item: NotificationEntity, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Banner(item.bannerImg, Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)))
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    item.title,
                    fontWeight = if (item.isRead) FontWeight.Normal else FontWeight.Bold,
                    maxLines = 1, overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(item.description, maxLines = 2, overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium)
                Text(formatTime(item.timestamp), style = MaterialTheme.typography.labelSmall)
                Text(if (item.isRead) "Read" else "Unread", style = MaterialTheme.typography.labelSmall,
                    color = if (item.isRead) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary)
            }
            if (!item.isRead) {
                Box(Modifier.size(10.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
            }
        }
    }
}
