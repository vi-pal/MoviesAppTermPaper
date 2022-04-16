package com.example.data.db

import androidx.room.*
import com.example.data.db.DatabaseContract.Movie.TABLE_MOVIES
import com.example.data.db.DatabaseContract.Person.TABLE_PEOPLE
import com.example.domain.entities.Movie
import com.example.domain.entities.Person

interface AppItemDao {
    @Dao
    interface MovieDao {
        @Query("SELECT exists (SELECT * from $TABLE_MOVIES where id=:id)")
        fun isFavorite(id: Int): Boolean

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun addMovie(movie: Movie)

        @Delete
        fun removeMovie(movie: Movie)

        @Query("SELECT * from $TABLE_MOVIES")
        fun getFavoriteMovies(): List<Movie>
    }

    @Dao
    interface PersonDao {
        @Query("SELECT exists (SELECT * from $TABLE_PEOPLE where id=:id)")
        fun isFavorite(id: Int): Boolean

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun addPerson(person: Person)

        @Delete
        fun removePerson(person: Person)

        @Query("SELECT * from $TABLE_PEOPLE")
        fun getFavoritePeople(): List<Person>
    }

}