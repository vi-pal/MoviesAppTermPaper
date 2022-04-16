package com.example.moviesapptermpaper.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.PersonRepository
import com.example.domain.entities.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val repository: PersonRepository): ViewModel() {

    val isFavoriteLiveData = repository.isFavoriteLiveData

    fun checkIsFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.isFavorite(id)
        }
    }

    fun addToFavorites(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorites(person)
        }
    }

    fun removeFromFavorites(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromFavorites(person)
        }
    }
}