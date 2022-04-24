package com.example.moviesapptermpaper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.util.Log
import android.view.LayoutInflater
import androidx.core.view.isGone
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.data.network.RetrofitService
import com.example.moviesapptermpaper.databinding.ActivityMainBinding
import com.example.moviesapptermpaper.service.NotificationWorker
import com.example.moviesapptermpaper.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    @Inject
    lateinit var retrofitService: RetrofitService

    override fun onStart() {
        super.onStart()
        setUpNavigation()
        NotificationWorker.startWorker(this, retrofitService)
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigation, navHostFragment.navController)
    }

    override fun handleConnectionState() {
        connectionManager.isConnected.observe(this) { isConnected ->
            Log.d("TAGAA", isConnected.toString())
            binding.noInternetConnection?.apply {
                if (isConnected && !isGone) {
                    animate().translationY(100F).setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            isGone = true
                        }
                    })
                } else if (!isConnected && isGone) {
                    animate().translationY(0F).setListener(null)
                    isGone = false

                }
            }
        }
    }
}

