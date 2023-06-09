package com.search.application.utils.mapper

import com.search.application.constant.Constant
import com.search.application.model.HomeListModel
import com.search.application.model.HomeListModel.HomeItemModel
import com.search.application.model.ResponseKaKaoSearch
import com.search.application.model.ResponseNaverSearch

fun ResponseNaverSearch.toModel() = HomeListModel(
    totalCount = this.total,
    start = this.start,
    list = this.itemModelList?.map { response ->
        HomeItemModel(
            type = Constant.TYPE.NAVER.toString(),
            title = response?.title ?: "",
            link = response?.link ?:"",
            thumbnail = response?.thumbnail ?:"",
            sizeWidth = response?.sizeWidth ?: 0,
            sizeHeight = response?.sizeHeight ?:0 )
    } ?: emptyList()
)


fun ResponseKaKaoSearch.toModel() = HomeListModel(
    totalCount = this.meta.totalCount,
    start = 0,
    list = this.itemModelList?.map { response ->
        HomeItemModel(
            type = Constant.TYPE.KAKAO.toString(),
            title = response?.collection ?: "",
            link = response?.docUrl ?: "",
            thumbnail = response?.thumbnailUrl ?: "",
            sizeWidth = response?.width ?: 0,
            sizeHeight = response?.height ?: 0)
    } ?: emptyList()

)