package com.v1.application.repository.retrofit

import com.search.application.model.ResponseHomeList
import com.search.application.model.ResponseKaKaoSearch
import com.search.application.model.ResponseNaverSearch
import com.v1.application.constant.APIConstant

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitApiInterface {

    @GET(APIConstant.SEARCH_TEXT_NAVER_URL)
    fun getNaverSearchText(
        @Header ("X-Naver-Client-Id") clientId: String ,
        @Header ("X-Naver-Client-Secret") clientSecret: String,
        @Query(APIConstant.QUERY) searchText:String,
        @Query(APIConstant.START) start:Int,
        @Query(APIConstant.DISPLAY) dis:Int
    ) : Call<ResponseNaverSearch>


    @GET(APIConstant.SEARCH_TEXT_KAKAO_URL)
    fun getKaKaoSearchText(
        @Header ("Authorization") apiKey: String,
        @Query(APIConstant.QUERY) searchText:String,
        @Query(APIConstant.PAGE) start:Int,
        @Query(APIConstant.SIZE) size:Int
    ) : Call<ResponseKaKaoSearch>

}