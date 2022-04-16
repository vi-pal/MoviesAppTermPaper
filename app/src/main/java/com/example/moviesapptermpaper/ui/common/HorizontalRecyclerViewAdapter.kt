package com.example.moviesapptermpaper.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.data.util.TYPE_MOVIE
import com.example.data.util.TYPE_PERSON
import com.example.domain.entities.AppItem
import com.example.domain.entities.Movie
import com.example.domain.entities.Person
import com.example.moviesapptermpaper.databinding.ItemRvHorizontalBinding
import com.example.moviesapptermpaper.ui.base.BaseRecyclerViewAdapter

class HorizontalRecyclerViewAdapter(list: List<AppItem>)
    : BaseRecyclerViewAdapter(list) {

    inner class MovieViewHolder(private val binding: ItemRvHorizontalBinding): BaseMovieViewHolder(binding) {
        override fun bind(item: Movie) {
            binding.tvName.text = item.title
            loadPicture(item.poster, binding.ivPoster)
            onItemClick(item, binding.rvItem)
        }
    }

    inner class PersonViewHolder(private val binding: ItemRvHorizontalBinding): BasePersonViewHolder(binding) {
        override fun bind(item: Person) {
            binding.tvName.text = item.name
            loadPicture(item.image, binding.ivPoster)
            onItemClick(item, binding.rvItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding = ItemRvHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when (viewType) {
            TYPE_MOVIE -> MovieViewHolder(binding)
            TYPE_PERSON -> PersonViewHolder(binding)
            else -> throw IllegalArgumentException("invalid ViewType")
        }
    }
}