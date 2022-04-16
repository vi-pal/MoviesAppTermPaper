package com.example.moviesapptermpaper.ui.base

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.ViewBinding
import com.example.data.util.APP_THEME
import javax.inject.Inject

abstract class BaseActivity<B: ViewBinding>: AppCompatActivity() {

    private var _binding: B? = null
    val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    abstract val bindingInflater: (LayoutInflater) -> B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setAppTheme()
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)

        title = ""
    }

    private fun setAppTheme() {
        val isDark = sharedPreferences.getBoolean(APP_THEME, true)
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        delegate.applyDayNight()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}