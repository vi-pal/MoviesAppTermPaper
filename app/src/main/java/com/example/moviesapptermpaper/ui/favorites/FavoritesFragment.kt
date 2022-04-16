package com.example.moviesapptermpaper.ui.favorites

import android.animation.ObjectAnimator
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.example.data.util.APP_THEME
import com.example.data.util.REQUEST_CODE
import com.example.data.util.showToast
import com.example.domain.entities.AppItem
import com.example.moviesapptermpaper.MainActivity
import com.example.moviesapptermpaper.R
import com.example.moviesapptermpaper.databinding.FragmentFavoritesBinding
import com.example.moviesapptermpaper.service.DownloadPdfForegroundService
import com.example.moviesapptermpaper.ui.base.BaseFragment
import com.example.moviesapptermpaper.ui.common.VerticalRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoritesBinding
        get() = FragmentFavoritesBinding::inflate

    private val viewModel by viewModels<FavoritesViewModel>()

    @Inject lateinit var sharedPreferences: SharedPreferences

    override fun onStart() {
        super.onStart()
        setSwitchState()
        viewModel.getFavoritesList()
        observeLiveData()
        switchListener()
        setClickListeners()
    }

    private fun observeLiveData() {
        viewModel.favoritesLivedata.observe(viewLifecycleOwner) { favorites ->
            if (favorites.isNotEmpty()) {
                binding.rvFavorites.visibility = View.VISIBLE
                initAdapter(favorites)
            }
            else {
                binding.rvFavorites.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            }
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            when (it) {
                null -> showToast(getString(R.string.list_is_empty))
                true -> showToast(getString(R.string.saved))
                false -> showToast(getString(R.string.error))
            }
            DownloadPdfForegroundService.stopService(requireContext())
        }
    }

    private fun initAdapter(favoritesList: MutableList<AppItem>) {
        adapter = VerticalRecyclerViewAdapter(favoritesList)
        adapter!!.onClick = { item: AppItem -> showDetailsOf(item) }
        binding.rvFavorites.adapter = adapter
    }

    private fun switchListener() {
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(APP_THEME, isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            (requireActivity() as MainActivity).delegate.applyDayNight()
        }
    }

    private fun setSwitchState() {
        binding.switchTheme.isChecked = sharedPreferences.getBoolean(APP_THEME, true)
    }

    private fun setClickListeners() {
        binding.ivDownloadPdf.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (activity?.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, REQUEST_CODE)
                } else {
                    startDownload()
                    startAnimation(it)
                }
            } else {
                startDownload()
                startAnimation(it)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownload()
            } else showToast(getString(R.string.permission_denied))
        }
    }

    private fun startDownload() {
        DownloadPdfForegroundService.startService(requireContext())
        viewModel.downloadPdf()
    }

    private fun startAnimation(view: View) {
        val objectAnimator = ObjectAnimator
            .ofFloat(view, "rotation", 0f, 360f)
            .setDuration(2000)
        objectAnimator.start()
    }

}