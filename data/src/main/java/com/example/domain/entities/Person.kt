package com.example.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.db.DatabaseContract.Person.COLUMN_MOVIES
import com.example.data.db.DatabaseContract.Person.TABLE_PEOPLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = TABLE_PEOPLE)
data class Person(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val popularity: Double,
    @SerializedName("profile_path") var image: String?,

    @ColumnInfo(name = COLUMN_MOVIES)
    @SerializedName("known_for") var movies: List<Movie>
): AppItem()
