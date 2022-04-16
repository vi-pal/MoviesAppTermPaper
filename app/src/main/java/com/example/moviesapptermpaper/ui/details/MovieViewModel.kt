package com.example.moviesapptermpaper.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.MovieRepository
import com.example.domain.entities.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {

    val isFavoriteLiveData = repository.isFavoriteLiveData

    fun checkIsFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.isFavorite(id)
        }
    }

    fun addToFavorites(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorites(movie)
        }
    }

    fun removeFromFavorites(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromFavorites(movie)
        }
    }
}