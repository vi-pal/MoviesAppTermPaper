package com.example.moviesapptermpaper.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.AppItemDao
import com.example.data.db.AppItemDatabase
import com.example.data.db.DatabaseContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideMovieDao(appDB: AppItemDatabase): AppItemDao.MovieDao {
        return appDB.getMovieDao()
    }

    @Provides
    fun providePersonDao(appDB: AppItemDatabase): AppItemDao.PersonDao {
        return appDB.getPersonDao()
    }

    @Provides
    fun provideAppItemDatabase(@ApplicationContext context: Context): AppItemDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppItemDatabase::class.java,
            DatabaseContract.DB_NAME
        ).build()
    }
}