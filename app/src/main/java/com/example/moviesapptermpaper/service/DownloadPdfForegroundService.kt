package com.example.moviesapptermpaper.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.data.util.PDF_CHANNEL_ID
import com.example.data.util.PDF_CHANNEL_NAME
import com.example.moviesapptermpaper.R
import com.example.moviesapptermpaper.MainActivity

class DownloadPdfForegroundService : Service() {

    companion object {
        fun startService(context: Context) {
            val intent = Intent(context, DownloadPdfForegroundService::class.java)
            ContextCompat.startForegroundService(context, intent)
        }

        fun stopService(context: Context) {
            val intent = Intent(context, DownloadPdfForegroundService::class.java)
            context.stopService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundWithNotification()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun startForegroundWithNotification() {
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent
            .getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, PDF_CHANNEL_ID)
            .setContentTitle(getString(R.string.downloading))
            .setContentText(getString(R.string.downloading_list_of_favorites))
            .setSmallIcon(R.drawable.ic_download)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                PDF_CHANNEL_ID, PDF_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}