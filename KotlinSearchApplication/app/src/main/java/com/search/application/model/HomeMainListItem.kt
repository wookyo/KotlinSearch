package com.search.application.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import com.search.application.constant.Constant

data class HomeListModel(
    val totalCount: Int?,
    val start: Int?,
    var list : List<HomeItemModel> = emptyList()){

    data class HomeItemModel(
        var type: String,
        var title: String = "",
        var link: String = "",
        var thumbnail: String = "",
        var sizeWidth: Int = 0,
        var sizeHeight: Int = 0,
        var isZzim :Boolean = false

    ): Parcelable {
        constructor(source: Parcel) : this(
            type = source?.readString() ?: "",
            title = source?.readString() ?: "",
            link = source?.readString() ?: "",
            thumbnail = source?.readString() ?: "",
            sizeWidth = source?.readInt() ?: 0,
            sizeHeight = source?.readInt() ?: 0,
            isZzim = source.readByte() != 0.toByte()
        )

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(title)
            dest.writeString(link)
            dest.writeString(thumbnail)
            dest.writeInt(sizeWidth)
            dest.writeInt(sizeHeight)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                dest.writeBoolean(isZzim)
            }
        }

        companion object CREATOR : Parcelable.Creator<HomeItemModel> {
            override fun createFromParcel(parcel: Parcel): HomeItemModel {
                return HomeItemModel(parcel)
            }

            override fun newArray(size: Int): Array<HomeItemModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}

