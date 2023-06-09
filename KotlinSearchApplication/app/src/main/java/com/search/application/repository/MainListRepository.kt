package com.v1.application.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.search.application.constant.Constant
import com.search.application.utils.mapper.toModel
import com.search.application.model.HomeListModel.HomeItemModel
import com.search.application.model.ResponseKaKaoSearch
import com.search.application.model.ResponseNaverSearch
import com.search.application.utils.PrefMgr
import com.v1.application.constant.APIConstant
import com.v1.application.repository.retrofit.RetrofitClient
import com.v1.application.utils.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainListRepository {
    private val TAG: String = MainListRepository::class.java.simpleName

    private val list : MutableList<HomeItemModel> = ArrayList()
    private val _itemList = MutableLiveData<List<HomeItemModel>>()
    private val itemList get() = _itemList

    private val favoriteList : MutableList<HomeItemModel> = ArrayList()
    private val _favoriteItems = MutableLiveData<List<HomeItemModel>>()
    private val favoriteItems get() = _favoriteItems

    init {
        _itemList.value = list
        _favoriteItems.value = favoriteList
    }

    fun getMainListLiveData(): MutableLiveData<List<HomeItemModel>> {
        return itemList
    }

    fun requestMainList(searchText:String, offset: Int) {
        requestNaverSearchText(searchText, offset)
        requestKakaoSearchText(searchText, offset)
    }

    fun clearMainList(){
        list.clear()
        _itemList.value = list
    }

    private fun addMainList(newItems: List<HomeItemModel>?) {
        newItems?.let {
            list.addAll( checkFavoriteItem(newItems))
            _itemList.value = list
        }
    }

    private fun removeMainList(item: HomeItemModel) {
        list.remove(item)
        _itemList.value = list
    }

    /**
     * naver search text
     * */
    fun requestNaverSearchText(searchText:String, offset: Int ){
        val call = RetrofitClient.apiInterface.getNaverSearchText(
            APIConstant.SEARCH_TEXT_NAVER_CLIENT_ID,
            APIConstant.SEARCH_TEXT_NAVER_CLIENT_SECRET,
            searchText,
            offset,
            10
        )
        call.enqueue(object: Callback<ResponseNaverSearch> {
            override fun onFailure(call: Call<ResponseNaverSearch?>, t: Throwable) {
                LogUtils.e(TAG, "[requestNaverSearchText] fail : "+t.message.toString())
            }

            override fun onResponse(call: Call<ResponseNaverSearch?>, response: Response<ResponseNaverSearch?>) {
                response.body()?.let { data ->
                    addMainList(data.toModel().list)
                }
            }
        })
    }

    fun requestKakaoSearchText(searchText:String, offset: Int ) {
        val call = RetrofitClient.apiInterface.getKaKaoSearchText(
            APIConstant.SEARCH_TEXT_KAKAO_REST_API_KEY,
            searchText,
            offset,
            10
        )
        call.enqueue(object : Callback<ResponseKaKaoSearch> {
            override fun onFailure(call: Call<ResponseKaKaoSearch?>, t: Throwable) {
                LogUtils.e(TAG, "[requestKakaoSearchText] fail : " + t.message.toString())
            }

            override fun onResponse(call: Call<ResponseKaKaoSearch?>, response: Response<ResponseKaKaoSearch?>) {
                response.body()?.let { data ->
                    addMainList(data.toModel().list)
                }
            }
        })
    }


    fun getFavoriteLiveData(): MutableLiveData<List<HomeItemModel>>{
        return favoriteItems
    }

    fun updateFavoriteItem(item: HomeItemModel?) {
        item?.let { item ->
            if (item.isZzim) {
                if (!favoriteList.contains(item)) favoriteList.add(item)
            } else {
                favoriteList.remove(item)
            }
            PrefMgr.put(Constant.PREF_KEY_MAIN_SELECT, favoriteList)
            _favoriteItems.value = favoriteList
        }
    }

    fun requestFavoriteList(){
        val listType = object : TypeToken<ArrayList<HomeItemModel>>() {}.type
        val items: ArrayList<HomeItemModel>? = PrefMgr.getDataArray(Constant.PREF_KEY_MAIN_SELECT, listType)

        items?.let { items ->
            items.forEach { item ->
                if(!favoriteList.contains(item)) favoriteList.add(item)
            }
            favoriteItems.value = favoriteList
        }
    }

    fun checkFavoriteItem(newItems: List<HomeItemModel>): List<HomeItemModel> {
            val listType = object : TypeToken<ArrayList<HomeItemModel>>() {}.type
            val saveList: ArrayList<HomeItemModel>? = PrefMgr.getDataArray(Constant.PREF_KEY_MAIN_SELECT, listType)
            saveList?.let {
               val myMap = saveList.map { "key" to it.link}.toMap()
                newItems.forEach { item ->
                    if(myMap!!.containsValue(item.link)){
                        item.isZzim = true
                    }
                }
            }
        return newItems
    }

}