package com.nhkim.applemarket

import android.os.Parcel
import android.os.Parcelable

data class Item(
    val title: String?,
    val content: String?,
    val seller: String?,
    val price: Int,
    val itemImage: Int,
    val address: String?,
    val like: Int,
    val chat: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(seller)
        parcel.writeInt(price)
        parcel.writeInt(itemImage)
        parcel.writeString(address)
        parcel.writeInt(like)
        parcel.writeInt(chat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }


}

