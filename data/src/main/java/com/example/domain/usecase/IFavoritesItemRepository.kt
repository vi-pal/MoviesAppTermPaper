package com.example.domain.usecase

import com.example.domain.entities.AppItem

interface IFavoritesItemRepository<T: AppItem> {

    fun isFavorite(id: Int)

    fun addToFavorites(item: T)

    fun removeFromFavorites(item: T)
}