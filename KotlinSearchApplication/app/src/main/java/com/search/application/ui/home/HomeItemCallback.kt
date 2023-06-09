package com.v1.application.view.main

import com.search.application.model.HomeListModel.HomeItemModel

interface HomeItemCallback {
    fun onClick (item: HomeItemModel)

    //fun onClickZzim(item: HomeItemModel)
}