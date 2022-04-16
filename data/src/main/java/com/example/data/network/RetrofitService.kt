package com.example.data.network

import com.example.domain.entities.MovieResponse
import com.example.domain.entities.PersonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    private val API_KEY: String
        get() = "57efaf8cc1dec4e885536c48dbce2146"

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String = API_KEY): Call<MovieResponse>

    @GET("trending/person/week")
    fun getPopularPeople(@Query("api_key") apiKey: String = API_KEY): Call<PersonResponse>

    @GET("search/movie")
    fun getSearchResults(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("trending/movie/day")
    fun getTrendingMovies(@Query("api_key") apiKey: String = API_KEY): Call<MovieResponse>
}