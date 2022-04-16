package com.example.moviesapptermpaper.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.data.util.SELECTED_ITEM
import com.example.domain.entities.AppItem
import com.example.moviesapptermpaper.ui.details.DetailsActivity

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    private var _binding: B? = null
    val binding get() = _binding!!

    var adapter: BaseRecyclerViewAdapter? = null

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
    }

    // to show details of selected item in DetailsActivity
    fun showDetailsOf(item: AppItem) {
        Intent(requireActivity(), DetailsActivity::class.java).apply {
            putExtra(SELECTED_ITEM, item)
        }.also { startActivity(it) }
    }

}