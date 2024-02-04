package com.nhkim.imagecollector.data.image

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("documents")
    val documents: List<Document>
)