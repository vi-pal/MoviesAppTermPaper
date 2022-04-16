package com.example.moviesapptermpaper.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.data.util.SELECTED_ITEM
import com.example.data.util.showToast
import com.example.domain.entities.Movie
import com.example.moviesapptermpaper.R
import com.example.moviesapptermpaper.databinding.FragmentMovieBinding
import com.example.moviesapptermpaper.ui.base.BaseFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieBinding
        get() = FragmentMovieBinding::inflate

    private val viewModel by viewModels<MovieViewModel>()

    override fun onStart() {
        super.onStart()
        val movie = requireActivity().intent.getSerializableExtra(SELECTED_ITEM) as Movie
        showInfo(movie)
        viewModel.checkIsFavorite(movie.id)
        observeLiveData()
        setClickListeners(movie)
    }

    private fun showInfo(movie: Movie) {
        with(binding) {
            Picasso.get().load(movie.poster).into(ivPoster)
            tvTitle.text = movie.title
            tvRating.text = movie.rating
            tvVotes.text = movie.voteCount
            tvReleaseDate.text = movie.releaseDate
            tvOverview.text = movie.overview
        }
    }

    private fun observeLiveData() {
        viewModel.isFavoriteLiveData.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) binding.btnFavorites.setImageResource(R.drawable.ic_favorites_add_filled)
            else binding.btnFavorites.setImageResource(R.drawable.ic_favorites_add)
        }
    }

    private fun setClickListeners(movie: Movie) {
        binding.btnFavorites.setOnClickListener {
            if (viewModel.isFavoriteLiveData.value == false) {
                viewModel.addToFavorites(movie)
                showToast(getString(R.string.saved_to_favorites))
            } else {
                viewModel.removeFromFavorites(movie)
                showToast(getString(R.string.removed_from_favorites))
            }
        }
    }
}