package com.example.domain.entities

import com.google.gson.annotations.SerializedName

data class PersonResponse(
    val page: Int,
    val results: List<Person>,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int
)
