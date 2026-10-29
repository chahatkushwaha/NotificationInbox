package com.assignment.notificationinbox

import android.app.Application
import com.assignment.notificationinbox.fcm.NotificationHelper

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannel(this)
    }
}
