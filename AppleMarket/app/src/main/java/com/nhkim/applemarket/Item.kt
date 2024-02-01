package com.nhkim.applemarket

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val title: String?,
    val content: String?,
    val seller: String?,
    val price: Int,
    val itemImage: Int,
    val address: String?,
    var like: Int,
    val chat: Int,
    var isLike: Boolean
) : Parcelable