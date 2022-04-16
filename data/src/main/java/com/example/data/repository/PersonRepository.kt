package com.example.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.data.db.AppItemDao
import com.example.data.util.TAG_ROOM_DB
import com.example.domain.entities.Person
import com.example.domain.usecase.IFavoritesItemRepository
import javax.inject.Inject

class PersonRepository @Inject constructor(private val personDao: AppItemDao.PersonDao): IFavoritesItemRepository<Person> {

    val isFavoriteLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun isFavorite(id: Int) {
        isFavoriteLiveData.postValue(personDao.isFavorite(id))
        Log.d(TAG_ROOM_DB, "Person isFavorite - ${isFavoriteLiveData.value}")
    }

    override fun addToFavorites(item: Person) {
        personDao.addPerson(item)
        isFavorite(item.id)
    }

    override fun removeFromFavorites(item: Person) {
        personDao.removePerson(item)
        isFavorite(item.id)
    }
}