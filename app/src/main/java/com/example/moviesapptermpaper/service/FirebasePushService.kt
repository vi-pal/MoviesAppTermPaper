package com.example.moviesapptermpaper.service

import android.util.Log
import com.example.data.util.PUSH_NOTIFICATION_CHANNEL_ID
import com.example.data.util.PUSH_NOTIFICATION_CHANNEL_NAME
import com.example.data.util.TAG_FIREBASE_MESSAGING
import com.example.data.util.createNotification
import com.example.moviesapptermpaper.R
import com.example.moviesapptermpaper.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebasePushService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG_FIREBASE_MESSAGING, "token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            createNotification(
                applicationContext,
                PUSH_NOTIFICATION_CHANNEL_ID,
                PUSH_NOTIFICATION_CHANNEL_NAME,
                MainActivity::class.java,
                R.drawable.ic_home,
                remoteMessage.notification!!.title.toString(),
                remoteMessage.notification!!.body.toString()
            )
        }
    }
}