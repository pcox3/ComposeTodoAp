package com.baseproject.utility

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
 fun askNotificationPermission(activity: Activity) {
    if (ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
    }
}