package com.search.application.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.search.application.databinding.FragmentFavoriteBinding
import com.search.application.model.HomeListModel.HomeItemModel
import com.search.application.ui.home.HomeViewModel
import com.v1.application.view.main.HomeItemCallback
import com.v1.application.view.main.HomeListAdapter

class FavoriteFragment: Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    private lateinit var mAdapter: HomeListAdapter
    private var offset :Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.run {
            favoriteListView.apply {
                layoutManager =  GridLayoutManager(context,2)
                mAdapter = HomeListAdapter(object : HomeItemCallback {
                    override fun onClick(item: HomeItemModel) {
                        item.isZzim = !item.isZzim
                        mAdapter.notifyItemChanged(mAdapter.getItemPosition(item))
                        viewModel.updateSelectedFavoriteItem(item)
                    }
                })
                adapter = mAdapter

                addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastVisibleItemPosition =
                            (recyclerView.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                        val itemTotalCount = recyclerView.adapter!!.itemCount
                        if (lastVisibleItemPosition >= itemTotalCount - 1) {
                            if (offset * 10 < itemTotalCount) {
                                offset++

                            }
                        }
                    }
                })
            }

        }
    }

    private fun initData() {
        viewModel.getFavoriteLiveData().observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                mAdapter.setItemList(it)
                mAdapter.notifyItemRangeChanged(0, it.size)
            }
        })
        viewModel.requestFavoriteList()
    }

}