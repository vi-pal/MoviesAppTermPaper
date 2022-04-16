package com.example.moviesapptermpaper.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.data.network.RetrofitService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var  retrofitService: RetrofitService

    override fun onReceive(context: Context, intent: Intent) {
        val action: String? = intent.action
        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            NotificationWorker.startWorker(context, retrofitService)
        }
    }
}