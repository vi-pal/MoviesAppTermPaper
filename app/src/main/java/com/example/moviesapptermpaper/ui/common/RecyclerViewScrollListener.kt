package com.example.moviesapptermpaper.ui.common

import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewScrollListener(
    private val listener: OnLoadItemsListener,
    private val observedView: EditText
    ): RecyclerView.OnScrollListener() {

    private var offset: Int = 1

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (isLastItemVisible(recyclerView)) {
            offset++
            listener.onAdapterScrolled(offset, observedView)    // if last item visible presenter loads more
        }
    }

    private fun isLastItemVisible(recyclerView: RecyclerView): Boolean {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val lastPositionVisible = layoutManager.findLastVisibleItemPosition()
        val itemCount = recyclerView.adapter?.itemCount ?: 0
        return lastPositionVisible + 1 >= itemCount
    }


    interface OnLoadItemsListener {
        fun onAdapterScrolled(offset: Int, observedView: EditText)
    }
}