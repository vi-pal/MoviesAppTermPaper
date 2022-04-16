package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.mappers.DataMapper
import com.example.domain.entities.Movie
import com.example.domain.entities.Person

@Database(entities = [Movie::class, Person::class], version = 1, exportSchema = false)
@TypeConverters(DataMapper::class)
abstract class AppItemDatabase: RoomDatabase() {
    abstract fun getMovieDao(): AppItemDao.MovieDao
    abstract fun getPersonDao(): AppItemDao.PersonDao
}