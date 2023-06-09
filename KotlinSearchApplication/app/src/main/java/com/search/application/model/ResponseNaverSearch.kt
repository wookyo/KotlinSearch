package com.search.application.model

import com.google.gson.annotations.SerializedName
import com.search.application.ui.home.HomeViewModel

data class ResponseNaverSearch (
    @SerializedName("lastBuildDate")
    val lastBuileDate: String?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("start")
    val start: Int?,
    @SerializedName(value = "items")
    val itemModelList: List<NaverSearchItem?>?){

    data class NaverSearchItem(
        @SerializedName("title")
        val title: String?,
        @SerializedName("link")
        val link: String?,
        @SerializedName("thumbnail")
        val thumbnail: String?,
        @SerializedName("sizeheight")
        val sizeHeight: Int?,
        @SerializedName("sizewidth")
        val sizeWidth: Int?,
        var zzim:Boolean)

}