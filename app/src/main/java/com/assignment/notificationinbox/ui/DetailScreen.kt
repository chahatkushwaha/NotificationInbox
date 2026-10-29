package com.assignment.notificationinbox.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.assignment.notificationinbox.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(onBack: () -> Unit, vm: DetailViewModel = viewModel()) {
    val item by vm.item.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Details") },
            navigationIcon = {
                IconButton(onClick = onBack) { Icon(painterResource(R.drawable.ic_arrow_back), "Back") }
            }
        )
    }) { padding ->
        val n = item
        if (n == null) {
            Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) { Text("Notification not found") }
        } else {
            Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
                Banner(n.bannerImg, Modifier.fillMaxWidth().height(220.dp))
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(n.title, style = MaterialTheme.typography.headlineSmall)
                    Text(formatTime(n.timestamp), style = MaterialTheme.typography.labelMedium)
                    Text(n.description, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
