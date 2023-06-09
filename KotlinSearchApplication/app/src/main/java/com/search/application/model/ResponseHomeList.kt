package com.search.application.model

import com.google.gson.annotations.SerializedName

data class ResponseHomeList(
    @SerializedName("total_count")
    val pageCount: Int?,
    @SerializedName("incomplete_results")
    val result: Boolean?,
    @SerializedName(value = "items")
    val itemModelList: ArrayList<HomeListItem>,
    var title: String){

    data class HomeListItem(
        @SerializedName("id")
        val id: String?,
        @SerializedName("avatar_url")
        val avatarUrl: String?,
        @SerializedName("login")
        val login: String?,
        @SerializedName("html_url")
        val htmlUrl: String?,
        var zzim:Boolean)
}
