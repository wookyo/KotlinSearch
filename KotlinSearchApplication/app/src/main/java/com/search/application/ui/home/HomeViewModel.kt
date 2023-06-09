package com.search.application.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.search.application.model.HomeListModel.HomeItemModel
import com.search.application.utils.PrefMgr
import com.v1.application.repository.MainListRepository
import com.v1.application.utils.LogUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class HomeViewModel : ViewModel() {
    private var repository: MainListRepository = MainListRepository()

    private val _selectedFavoriteItem = MutableLiveData<HomeItemModel>()
    private val selectedFavoriteItem get() = _selectedFavoriteItem

    fun getMainListLiveData(): MutableLiveData<List<HomeItemModel>>{
        return repository.getMainListLiveData()
    }

    fun requestMainList(searchText:String, offset: Int){
        viewModelScope.launch {
            repository.requestMainList(searchText, offset)
        }
    }

    fun clearMainList(){
        viewModelScope.launch {
            repository.clearMainList()
        }
    }

    fun getFavoriteLiveData(): MutableLiveData<List<HomeItemModel>>{
        return repository.getFavoriteLiveData()
    }

    fun updateFavoriteItem(item: HomeItemModel){
        viewModelScope.launch {
            repository.updateFavoriteItem(item)
        }
    }

    fun requestFavoriteList(){
        viewModelScope.launch {
            repository.requestFavoriteList()
        }
    }

    fun getSelectedFavoriteLiveData(): MutableLiveData<HomeItemModel>{
        return selectedFavoriteItem
    }

    fun updateSelectedFavoriteItem(item: HomeItemModel){
        viewModelScope.launch {
            _selectedFavoriteItem.value = item
        }
        updateFavoriteItem(item)
    }

}