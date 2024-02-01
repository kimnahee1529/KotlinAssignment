package com.nhkim.imagecollector.data.image

import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("total_count")
    val total_count: Int,
    @SerializedName("pageable_count")
    val pageable_count: Int,
    @SerializedName("is_end")
    val is_end: Boolean
)