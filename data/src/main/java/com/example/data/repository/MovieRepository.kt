package com.example.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.data.db.AppItemDao
import com.example.data.util.TAG_ROOM_DB
import com.example.domain.entities.Movie
import com.example.domain.usecase.IFavoritesItemRepository
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieDao: AppItemDao.MovieDao): IFavoritesItemRepository<Movie> {

    val isFavoriteLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun isFavorite(id: Int) {
        isFavoriteLiveData.postValue(movieDao.isFavorite(id))
        Log.d(TAG_ROOM_DB, "Movie isFavorite ${isFavoriteLiveData.value}")
    }

    override fun addToFavorites(item: Movie) {
        movieDao.addMovie(item)
        isFavorite(item.id)
    }

    override fun removeFromFavorites(item: Movie) {
        movieDao.removeMovie(item)
        isFavorite(item.id)
    }
}