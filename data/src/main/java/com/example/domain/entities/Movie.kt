package com.example.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.db.DatabaseContract.Movie.COLUMN_RELEASE_DATE
import com.example.data.db.DatabaseContract.Movie.COLUMN_VOTE_COUNT
import com.example.data.db.DatabaseContract.Movie.TABLE_MOVIES
import com.google.gson.annotations.SerializedName

@Entity(tableName = TABLE_MOVIES)
data class Movie(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    @SerializedName("poster_path") var poster: String?,

    @ColumnInfo(name = COLUMN_RELEASE_DATE)
    @SerializedName("release_date")
    val releaseDate: String,

    @ColumnInfo(name = COLUMN_VOTE_COUNT)
    @SerializedName("vote_count") val voteCount: String,

    @SerializedName("vote_average") val rating: String,
    val overview: String
): AppItem()