package com.search.application.model

import com.google.gson.annotations.SerializedName

data class ResponseKaKaoSearch (
    @SerializedName(value = "documents")
    val itemModelList: List<KaKaoSearchItem?>?,

    @SerializedName(value = "meta")
    val meta: KaKaoSearchMeta){

    data class KaKaoSearchItem(
        @SerializedName("collection")
        val collection: String?,
        @SerializedName("datetime")
        val datetime: String?,
        @SerializedName("display_sitename")
        val display_sitename: String?,
        @SerializedName("doc_url")
        val docUrl: String?,
        @SerializedName("image_url")
        val imageUrl: String?,
        @SerializedName("thumbnail_url")
        val thumbnailUrl: String?,
        @SerializedName("width")
        val width: Int?,
        @SerializedName("height")
        val height: Int?,
        var zzim:Boolean)

    data class KaKaoSearchMeta(
        @SerializedName("is_end")
        val isEnd: Boolean,
        @SerializedName("pageable_count")
        val pageableCount: Int,
        @SerializedName("total_count")
        val totalCount: Int,
    )
}