package com.example.moviesapptermpaper.ui.details

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.data.util.SELECTED_ITEM
import com.example.domain.entities.Movie
import com.example.domain.entities.Person
import com.example.moviesapptermpaper.R
import com.example.moviesapptermpaper.databinding.ActivityDetailsBinding
import com.example.moviesapptermpaper.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity<ActivityDetailsBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityDetailsBinding
        get() = ActivityDetailsBinding::inflate

    override fun onStart() {
        super.onStart()
        showDetails()
    }

    private fun showDetails() {
        when(intent.getSerializableExtra(SELECTED_ITEM)) {
            is Movie -> replaceFragment(MovieFragment())
            is Person -> replaceFragment(PersonFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.details_activity_container, fragment)
            .commit()
    }

//    override fun setAppTheme() {}
}