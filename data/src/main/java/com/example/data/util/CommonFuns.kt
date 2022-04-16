package com.example.data.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.example.domain.entities.Movie

fun Fragment.showToast(text: String) {
    Toast.makeText(activity?.applicationContext, text, Toast.LENGTH_SHORT).show()
}

fun createNotification(
    context: Context,
    channelID: String,
    channelName: String,
    activity: Class<*>,
    notificationImage: Int,
    notificationTitle: String,
    notificationText: String,
    movie: Movie? = null
) {
    val manager = context.getSystemService(NotificationManager::class.java)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelID, channelName, NotificationManager.IMPORTANCE_HIGH
        )
        manager.createNotificationChannel(notificationChannel)
    }

    // intent to open application
    val intent = movie?.let {
        Intent(context, activity).apply { putExtra(SELECTED_ITEM, movie) }
    }
        ?: Intent(context, activity)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

    val notification = NotificationCompat.Builder(context, channelID)
        .setSmallIcon(notificationImage)
        .setContentTitle(notificationTitle)
        .setContentText(notificationText)
        .setContentIntent(pendingIntent)
        .build()

    manager.notify(2, notification)
}