package com.assignment.notificationinbox

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignment.notificationinbox.data.NotificationRepository
import com.assignment.notificationinbox.ui.DetailScreen
import com.assignment.notificationinbox.ui.InboxScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        com.google.firebase.messaging.FirebaseMessaging.getInstance().token
            .addOnCompleteListener { t ->
                if (t.isSuccessful) android.util.Log.d("InboxMessaging", "FCM token: ${t.result}")
               else android.util.Log.e("InboxMessaging", "Token fetch failed", t.exception)
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        if (savedInstanceState == null) saveBackgroundPayload(intent)
        setContent { MaterialTheme { AppNav() } }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        saveBackgroundPayload(intent)
    }


    private fun saveBackgroundPayload(intent: Intent?) {
        val extras = intent?.extras ?: return
        val data = extras.keySet().mapNotNull { k -> extras.getString(k)?.let { k to it } }.toMap()
        if (!data.containsKey("title") && !data.containsKey("id")) return
        lifecycleScope.launch { NotificationRepository.from(applicationContext).saveFromData(data) }
    }
}

@Composable
private fun AppNav() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "inbox") {
        composable("inbox") { InboxScreen(onOpen = { nav.navigate("detail/${Uri.encode(it)}") }) }
        composable("detail/{id}") { DetailScreen(onBack = { nav.popBackStack() }) }
    }
}
