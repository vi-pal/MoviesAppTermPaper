package com.example.moviesapptermpaper.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: FavoritesRepository): ViewModel() {

    val favoritesLivedata = repository.favoritesLiveData
    val loadingLiveData = repository.loadingLiveData

    fun getFavoritesList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites()
        }
    }

    fun downloadPdf() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveDocument()
        }
    }
}