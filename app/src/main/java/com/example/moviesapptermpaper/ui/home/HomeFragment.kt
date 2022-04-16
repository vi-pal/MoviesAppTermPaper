package com.example.moviesapptermpaper.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.AppItem
import com.example.moviesapptermpaper.databinding.FragmentHomeBinding
import com.example.moviesapptermpaper.ui.base.BaseFragment
import com.example.moviesapptermpaper.ui.common.HorizontalRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onStart() {
        super.onStart()
        viewModel.getMovies()
        viewModel.getPeople()
        observeLiveData()
        setClickListeners()
    }

    private fun observeLiveData() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner) {
            with(binding) {
                checkLiveDataResponse(it, rvMovies, btnErrorMovies, pbMovies)
            }
        }
        viewModel.peopleLiveData.observe(viewLifecycleOwner) {
            with(binding) {
                checkLiveDataResponse(it, rvPeople, btnErrorPeople, pbPeople)
            }
        }
    }

    private fun initAdapter(recycler: RecyclerView, list: List<AppItem>) {
        adapter = HorizontalRecyclerViewAdapter(list)
        adapter!!.onClick = { item -> showDetailsOf(item) }
        recycler.adapter = adapter
    }

    private fun setClickListeners() {
        binding.btnErrorMovies.setOnClickListener {
            it.visibility = View.GONE
            binding.pbMovies.visibility = View.VISIBLE
            viewModel.getMovies()
        }

        binding.btnErrorPeople.setOnClickListener {
            it.visibility = View.GONE
            binding.pbPeople.visibility = View.VISIBLE
            viewModel.getPeople()
        }
    }

    private fun checkLiveDataResponse(
        response: List<AppItem>?, recyclerView: RecyclerView,
        errorButton: Button, progressBar: ProgressBar
    ) {
        if (response != null) {
            if (response.isNotEmpty()) {
                initAdapter(recyclerView, response)
                errorButton.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        } else {
            errorButton.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        }
        progressBar.visibility = View.INVISIBLE
    }

}