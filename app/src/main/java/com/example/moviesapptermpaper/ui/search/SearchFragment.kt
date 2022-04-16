package com.example.moviesapptermpaper.ui.search

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.example.domain.entities.AppItem
import com.example.moviesapptermpaper.databinding.FragmentSearchBinding
import com.example.moviesapptermpaper.ui.base.BaseFragment
import com.example.moviesapptermpaper.ui.common.RecyclerViewScrollListener
import com.example.moviesapptermpaper.ui.common.VerticalRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment: BaseFragment<FragmentSearchBinding>(), SearchContract.View {

    @Inject lateinit var presenter: SearchPresenter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun onStart() {
        super.onStart()
        presenter.search(binding.etSearch)
        { updateUiVisibilityOnStateChange(progressBar = View.VISIBLE) }
        setClickListeners()
        setSearchInputLayoutListener()
    }

    override fun showResult(movieList: MutableList<AppItem>) {
        if (movieList.isNotEmpty()){
            updateUiVisibilityOnStateChange(recyclerView = View.VISIBLE)
            initAdapter(movieList)
        } else updateUiVisibilityOnStateChange(emptyTextView = View.VISIBLE)

    }

    override fun updateResult(nextPageList: MutableList<AppItem>) {
        (adapter as VerticalRecyclerViewAdapter).updateList(nextPageList)
    }

    override fun showError() {
        updateUiVisibilityOnStateChange(errorButton = View.VISIBLE)
    }

    private fun initAdapter(movieList: MutableList<AppItem>) {
        adapter = VerticalRecyclerViewAdapter(movieList)
        binding.rvSearchResult.adapter = adapter
        adapter!!.onClick = { item: AppItem -> showDetailsOf(item) }

        // load on more data on scroll
        val scrollListener = RecyclerViewScrollListener(presenter, binding.etSearch)
        binding.rvSearchResult.addOnScrollListener(scrollListener)
    }

    // repeat search if error occurred
    private fun setClickListeners() {
        binding.btnError.setOnClickListener {
            presenter.repeatSearchRequest(binding.etSearch) {
                updateUiVisibilityOnStateChange(progressBar = View.VISIBLE)
            }
        }
    }

    private fun updateUiVisibilityOnStateChange(
        recyclerView: Int = View.GONE,
        progressBar: Int = View.GONE,
        emptyTextView: Int = View.GONE,
        errorButton: Int = View.GONE
    ) {
        activity?.runOnUiThread {
            binding.rvSearchResult.visibility = recyclerView
            binding.pbSearch.visibility = progressBar
            binding.tvEmpty.visibility = emptyTextView
            binding.btnError.visibility = errorButton
        }
    }

    // functions to set edit text position in search
    private fun setSearchInputLayoutListener() {
        binding.tilSearch.viewTreeObserver
            .addOnPreDrawListener(object :ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    if (binding.tilSearch.height > 0) {
                        binding.tilSearch.viewTreeObserver.removeOnPreDrawListener(this)
                        updateHintPosition(
                            binding.etSearch.hasFocus(),
                            !binding.etSearch.text.isNullOrEmpty(), false
                        )
                        return false
                    }
                    return true
                }
            })

        binding.etSearch.setOnFocusChangeListener { _, hasFocus ->
            updateHintPosition(hasFocus, !binding.etSearch.text.isNullOrEmpty(), true)
        }
    }

    private fun updateHintPosition(hasFocus: Boolean, hasText: Boolean, animate: Boolean) {
        if (animate) TransitionManager.beginDelayedTransition(binding.tilSearch)
        if (hasFocus || hasText) binding.etSearch.setPadding(0, 0, 0, 0)
        else binding.etSearch.setPadding(0, 0, 0, getTextInputLayoutTopSpace())
    }

    private fun getTextInputLayoutTopSpace(): Int {
        var currentView: View = binding.etSearch
        var space = 0
        do {
            space += currentView.top
            currentView = currentView.parent as View
        } while (currentView.id != binding.tilSearch.id)
        return space
    }

}