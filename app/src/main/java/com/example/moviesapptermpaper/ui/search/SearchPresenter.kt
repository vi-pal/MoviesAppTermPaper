package com.example.moviesapptermpaper.ui.search

import android.annotation.SuppressLint
import android.widget.EditText
import com.example.data.repository.SearchRepository
import com.example.data.util.REQUEST_DEFAULT_PAGE
import com.example.domain.entities.AppItem
import com.example.moviesapptermpaper.ui.common.RecyclerViewScrollListener
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchPresenter @Inject constructor(
    val view: SearchContract.View,
    private val repository: SearchRepository
): SearchContract.Presenter, RecyclerViewScrollListener.OnLoadItemsListener {

    // observes user input
    override fun search(observedView: EditText, doOnEach: () -> Unit) {
        RxTextView.textChangeEvents(observedView)
            .debounce(400, TimeUnit.MILLISECONDS)
            .filter { it.text().length > 2 || it.text().isEmpty() }
            .switchMap { repository.search(getSearchQuery(observedView)) }
            .observe(REQUEST_DEFAULT_PAGE, doOnEach)
    }

    // to make request in case of error
    override fun repeatSearchRequest(observedView: EditText, page: Int, doOnEach: () -> Unit) {
        repository.search(getSearchQuery(observedView), page).observe(page, doOnEach)
    }

    // load more items on scroll
    override fun onAdapterScrolled(offset: Int, observedView: EditText) {
        repeatSearchRequest(observedView, offset) {}
    }

    private fun getSearchQuery(inputField: EditText) = inputField.text.trim { it <= ' ' }.toString()

    @SuppressLint("CheckResult")
    private fun Observable<MutableList<AppItem>>.observe(page: Int, doOnEach: () -> Unit) {
        observeOn(AndroidSchedulers.mainThread())
        subscribeOn(AndroidSchedulers.mainThread())
        doOnEach { doOnEach.invoke() }
        subscribe(
            { result ->
                if (page == REQUEST_DEFAULT_PAGE) view.showResult(result)
                else view.updateResult(result)
            },
            { view.showError() }
        )
    }

}