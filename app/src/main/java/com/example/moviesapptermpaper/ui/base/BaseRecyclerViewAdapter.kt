package com.example.moviesapptermpaper.ui.base

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.data.util.TYPE_MOVIE
import com.example.data.util.TYPE_PERSON
import com.example.domain.entities.AppItem
import com.example.domain.entities.Movie
import com.example.domain.entities.Person
import com.squareup.picasso.Picasso

abstract class BaseRecyclerViewAdapter(private val list: List<*>):
    RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder<*>>() {

    var onClick: ((AppItem) -> Unit)? = null

    abstract inner class BaseViewHolder<in T>(binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: T)

        fun onItemClick(item: AppItem, itemView: View) {
            itemView.setOnClickListener {
                onClick?.invoke(item)
            }
        }
        fun loadPicture(path: String?, view: ImageView) {
            Picasso.get().load(path).into(view)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (holder is BaseMovieViewHolder) holder.bind(list[position] as Movie)
        else if (holder is BasePersonViewHolder)holder.bind(list[position] as Person)
    }

    override fun getItemViewType(position: Int): Int {
        return when(list[position]) {
            is Movie -> TYPE_MOVIE
            is Person -> TYPE_PERSON
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = list.size

    // abstract classes that must be implemented in adapter classes (used in onBindViewHolder())
    abstract inner class BaseMovieViewHolder(binding: ViewBinding): BaseViewHolder<Movie>(binding)
    abstract inner class BasePersonViewHolder(binding: ViewBinding): BaseViewHolder<Person>(binding)
}
