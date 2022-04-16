package com.example.moviesapptermpaper.ui.search

import android.widget.EditText
import com.example.domain.entities.AppItem

interface SearchContract {

    interface View {
        fun showResult(movieList: MutableList<AppItem>)
        fun updateResult(nextPageList: MutableList<AppItem>)
        fun showError()
    }

    interface Presenter {
        fun search(observedView: EditText, doOnEach: () -> Unit)
        fun repeatSearchRequest(observedView: EditText, page: Int = 1, doOnEach: () -> Unit)
    }
}