package com.nhkim.applemarket

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
    val like: Int,
    val chat: Int
) : Parcelable

