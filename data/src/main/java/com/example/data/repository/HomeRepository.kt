package com.example.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.data.mappers.DataMapper
import com.example.data.network.RetrofitService
import com.example.data.util.TAG_HOME_REPOSITORY
import com.example.domain.entities.Movie
import com.example.domain.entities.MovieResponse
import com.example.domain.entities.Person
import com.example.domain.entities.PersonResponse
import com.example.domain.usecase.IHomeRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(private val retrofitService: RetrofitService): IHomeRepository {

    val moviesLiveData: MutableLiveData<List<Movie>?> = MutableLiveData()
    val peopleLiveData: MutableLiveData<List<Person>?> = MutableLiveData()

    override fun getPopularMovies() {
        retrofitService.getPopularMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as MovieResponse
                    val receivedMovies = result.results
                    val finalMoviesList = mutableListOf<Movie>()
                    receivedMovies.forEach {
                        if (it.poster != null) finalMoviesList.add(DataMapper.mapMovie(it))
                    }
                    moviesLiveData.postValue(finalMoviesList)
                    Log.d(TAG_HOME_REPOSITORY, finalMoviesList.toString())
                }
                else {
                    Log.d(TAG_HOME_REPOSITORY, "in else onResponse + ${call.request().url()}")
                    moviesLiveData.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG_HOME_REPOSITORY, t.message.toString() + call.request().url())
                moviesLiveData.postValue(null)
            }
        })
    }

    override fun getPopularPeople() {
        retrofitService.getPopularPeople().enqueue(object : Callback<PersonResponse> {
            override fun onResponse(call: Call<PersonResponse>, response: Response<PersonResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as PersonResponse
                    val receivedPeople = result.results
                    val finalPeopleList = mutableListOf<Person>()
                    receivedPeople.forEach {
                        if (it.image != null) finalPeopleList.add(DataMapper.mapPerson(it))
                    }
                    peopleLiveData.postValue(finalPeopleList)
                } else {
                    Log.d(TAG_HOME_REPOSITORY, "in else onResponse + ${call.request().url()}")
                    peopleLiveData.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<PersonResponse>, t: Throwable) {
                Log.d(TAG_HOME_REPOSITORY, t.message.toString() + call.request().url())
                peopleLiveData.postValue(null)
            }
        })
    }
}