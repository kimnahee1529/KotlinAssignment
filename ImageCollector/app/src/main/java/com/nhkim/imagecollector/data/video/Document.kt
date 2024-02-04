package com.nhkim.imagecollector.data.video

import java.util.Date

data class Document(
    val title: String,
    val url: String,
    val datetime: Date,
    val play_time: Int,
    val thumbnail: String,
    val author: String,
    var isHearted: Boolean = false
)