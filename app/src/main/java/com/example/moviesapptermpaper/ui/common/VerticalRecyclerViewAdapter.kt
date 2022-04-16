package com.example.moviesapptermpaper.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.data.util.TYPE_MOVIE
import com.example.data.util.TYPE_PERSON
import com.example.domain.entities.AppItem
import com.example.domain.entities.Movie
import com.example.domain.entities.Person
import com.example.moviesapptermpaper.databinding.ItemRvVerticalMovieBinding
import com.example.moviesapptermpaper.databinding.ItemRvVerticalPersonBinding
import com.example.moviesapptermpaper.ui.base.BaseRecyclerViewAdapter

class VerticalRecyclerViewAdapter(private val itemList: MutableList<AppItem>): BaseRecyclerViewAdapter(itemList) {

    inner class MovieViewHolder(private val binding: ItemRvVerticalMovieBinding): BaseRecyclerViewAdapter.BaseMovieViewHolder(binding) {
        override fun bind(item: Movie) {
            with(binding) {
                loadPicture(item.poster, ivPoster)
                tvTitle.text = item.title
                tvReleaseDate.text = item.releaseDate
                tvRating.text = item.rating
                onItemClick(item, rvItem)
            }
        }
    }

    inner class PersonViewHolder(private val binding: ItemRvVerticalPersonBinding): BaseRecyclerViewAdapter.BasePersonViewHolder(binding) {
        override fun bind(item: Person) {
            with(binding) {
                loadPicture(item.image, ivImage)
                tvName.text = item.name
                tvPopularity.text = item.popularity.toString()
                onItemClick(item, rvItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewAdapter.BaseViewHolder<*> {
        return when (viewType) {
            TYPE_MOVIE -> {
                val binding = ItemRvVerticalMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieViewHolder(binding)
            }
            TYPE_PERSON -> {
                val binding = ItemRvVerticalPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PersonViewHolder(binding)
            }
            else -> throw IllegalArgumentException("invalid ViewType")
        }
    }

    fun updateList(nextPage: List<AppItem>) {
        itemList.addAll(nextPage)
    }
}