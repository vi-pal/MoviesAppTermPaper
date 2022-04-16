package com.example.data.repository

import android.util.Log
import com.example.data.mappers.DataMapper
import com.example.data.network.RetrofitService
import com.example.data.util.TAG_SEARCH_RESPONSE
import com.example.domain.entities.AppItem
import com.example.domain.entities.MovieResponse
import com.example.domain.usecase.ISearchRepository
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(private val retrofitService: RetrofitService) :
    ISearchRepository {

    // creating Observable object to listen it in presenter and change ui
    override fun search(searchQuery: String, page: Int): Observable<MutableList<AppItem>> {
        return Observable.create { subscriber ->
            if (searchQuery.isNotEmpty()) {
                val callback = object : Callback<MovieResponse> {
                    override fun onResponse(
                        call: Call<MovieResponse>,
                        response: Response<MovieResponse>
                    ) {
                        if (response.isSuccessful) {
                            val movieList = mutableListOf<AppItem>()
                            response.body()?.results?.forEach {
                                movieList.add(DataMapper.mapMovie(it))
                            }
                            subscriber.onNext(movieList)
                        } else {
                            Log.d(TAG_SEARCH_RESPONSE, response.errorBody().toString())
                            Log.d(TAG_SEARCH_RESPONSE, call.request().url().toString())
                            subscriber.onNext(mutableListOf())
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d(TAG_SEARCH_RESPONSE, t.message.toString())
                        Log.d(TAG_SEARCH_RESPONSE, call.request().url().toString())
                        subscriber.onError(t)
                    }
                }
                // REQUEST with callback
                retrofitService.getSearchResults(query = searchQuery, page = page).enqueue(callback)
            } else {
                subscriber.onNext(mutableListOf())
            }
        }
    }

}
