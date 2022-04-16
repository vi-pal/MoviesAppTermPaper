package com.example.domain.usecase

import com.example.domain.entities.AppItem
import io.reactivex.Observable

interface ISearchRepository {
    fun search(searchQuery: String, page: Int = 1): Observable<MutableList<AppItem>>
}