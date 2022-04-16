package com.example.data.mappers

import androidx.room.TypeConverter
import com.example.data.util.MEDIA_BASE_URL
import com.example.domain.entities.Movie
import com.example.domain.entities.Person
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataMapper {

    companion object {
        fun mapMovie(movie: Movie): Movie {
            movie.poster = MEDIA_BASE_URL + movie.poster
            return movie
        }

        fun mapPerson(person: Person): Person {
            person.image = MEDIA_BASE_URL + person.image
            val updatedMoviesList = mutableListOf<Movie>()
            person.movies.forEach {
                updatedMoviesList.add(mapMovie(it))
            }
            person.movies = updatedMoviesList
            return person
        }
    }

    @TypeConverter
    fun fromMoviesList(movies: List<Movie>): String {
        val type = object: TypeToken<List<Movie>>() {}.type
        return Gson().toJson(movies, type)
    }

    @TypeConverter
    fun toMoviesList(json: String): List<Movie> {
        val type = object : TypeToken<List<Movie>>() {}.type
        return Gson().fromJson(json, type)
    }
}