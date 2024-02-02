package com.nhkim.imagecollector.data.video

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("documents")
    val documents: List<Document>
)