package com.example.moviesapptermpaper.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    val moviesLiveData = repository.moviesLiveData
    val peopleLiveData = repository.peopleLiveData

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularMovies()
        }
    }
    fun getPeople() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularPeople()
        }
    }
}