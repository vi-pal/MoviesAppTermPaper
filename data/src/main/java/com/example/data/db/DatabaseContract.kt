package com.example.data.db

object DatabaseContract {

    const val DB_NAME = "favorites"

    object Movie {
        const val TABLE_MOVIES = "movies"
        const val COLUMN_RELEASE_DATE = "release_date"
        const val COLUMN_VOTE_COUNT = "vote_count"
    }

    object Person {
        const val TABLE_PEOPLE = "people"
        const val COLUMN_MOVIES = "movies_known_for"
    }
}