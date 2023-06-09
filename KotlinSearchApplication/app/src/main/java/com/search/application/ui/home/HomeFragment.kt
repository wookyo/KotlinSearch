package com.search.application.ui.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.search.application.databinding.FragmentHomeBinding
import com.search.application.model.HomeListModel.HomeItemModel
import com.search.application.utils.PrefMgr
import com.v1.application.utils.LogUtils

import com.v1.application.view.main.HomeListAdapter
import com.v1.application.view.main.HomeItemCallback


class HomeFragment : Fragment() {
    val TAG: String = HomeFragment::class.java.simpleName

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }
    private lateinit var mAdapter: HomeListAdapter
    private var offset :Int = 1
    private var searchText: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
            editView.apply {
                setOnEditorActionListener(object : TextView.OnEditorActionListener{
                    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                            || actionId == EditorInfo.IME_ACTION_DONE) {
                            editableText?.let {search ->
                                searchText(search.toString())

                            }?:run{
                                clearData()
                            }
                        }
                        return false
                    }
                })
            }
            btnGo.setOnClickListener {
                editView.editableText?.let {search ->
                    searchText(search.toString())
                }?:run{
                    clearData()
                }
            }

            mainListView.apply {
                layoutManager =  GridLayoutManager(context,2)
                mAdapter = HomeListAdapter(object : HomeItemCallback {
                    override fun onClick(item: HomeItemModel) {
                        item.isZzim = !item.isZzim
                        mAdapter.notifyItemChanged(mAdapter.getItemPosition(item))
                        viewModel.updateFavoriteItem(item)
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
                                searchText(searchText)
                            }
                        }

                    }
                })
            }
        }
    }

    private fun clearData(){
        offset = 1
        searchText = null
        viewModel.clearMainList()
    }

    private fun searchText(search: String?){
        if(search.isNullOrEmpty()){
            clearData()
            return
        }
        if(search != searchText){
            clearData()
        }

        search?.let { text ->
            viewModel.requestMainList(text, offset)
        }
        this.searchText = search
    }

    private fun initData() {
        context?.let { PrefMgr.init(context = it) }

        viewModel.getMainListLiveData().observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if(response.isEmpty()){
                    mAdapter.clear()
                    mAdapter.notifyDataSetChanged()
                }else {
                    mAdapter.setItemList(it)
                    mAdapter.notifyItemRangeChanged(offset*20, it.size)

                }
            } ?: kotlin.run {
                mAdapter.clear()
                mAdapter.notifyDataSetChanged()
            }
        })

        viewModel.getSelectedFavoriteLiveData().observe(viewLifecycleOwner, Observer { response ->
            response?.let { item->
                mAdapter.notifyItemChanged(mAdapter.getItemPosition(item))
            }
        })

    }
}