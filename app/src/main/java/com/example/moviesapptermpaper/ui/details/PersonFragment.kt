package com.example.moviesapptermpaper.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.data.util.SELECTED_ITEM
import com.example.data.util.showToast
import com.example.domain.entities.Movie
import com.example.domain.entities.Person
import com.example.moviesapptermpaper.R
import com.example.moviesapptermpaper.databinding.FragmentPersonBinding
import com.example.moviesapptermpaper.ui.base.BaseFragment
import com.example.moviesapptermpaper.ui.common.HorizontalRecyclerViewAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment: BaseFragment<FragmentPersonBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPersonBinding
        get() = FragmentPersonBinding::inflate

    private val viewModel by viewModels<PersonViewModel>()

    override fun onStart() {
        super.onStart()
        val person = requireActivity().intent.getSerializableExtra(SELECTED_ITEM) as Person
        showInfo(person)
        viewModel.checkIsFavorite(person.id)
        observeLiveData()
        setClickListeners(person)
    }

    private fun showInfo(person: Person) {
        with(binding) {
            initAdapter(person.movies)
            Picasso.get().load(person.image).into(ivImage)
            tvName.text = person.name
            tvPopularity.text = person.popularity.toString()
        }
    }

    private fun observeLiveData() {
        viewModel.isFavoriteLiveData.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) binding.btnFavorites.setImageResource(R.drawable.ic_favorites_add_filled)
            else binding.btnFavorites.setImageResource(R.drawable.ic_favorites_add)
        }
    }

    private fun setClickListeners(person: Person) {
        binding.btnFavorites.setOnClickListener {
            if (viewModel.isFavoriteLiveData.value == false) {
                viewModel.addToFavorites(person)
                showToast(getString(R.string.saved_to_favorites))
            } else {
                viewModel.removeFromFavorites(person)
                showToast(getString(R.string.removed_from_favorites))
            }
        }
    }

    private fun initAdapter(movies: List<Movie>) {
        adapter = HorizontalRecyclerViewAdapter(movies)
        adapter!!.onClick = { item -> showDetailsOf(item) }
        binding.rvMoviesKnownFor.adapter = adapter
    }
}