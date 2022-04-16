package com.example.moviesapptermpaper

import android.view.LayoutInflater
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

//    @Inject lateinit var sharedPreferences: SharedPreferences
    @Inject lateinit var retrofitService: RetrofitService

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

    /*@RequiresApi(Build.VERSION_CODES.M)
    override fun setAppTheme() {
        val isDark = sharedPreferences.getBoolean(APP_THEME, true)
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        delegate.applyDayNight()
    }*/

}

