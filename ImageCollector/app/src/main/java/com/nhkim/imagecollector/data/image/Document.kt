package com.nhkim.imagecollector.data.image

import java.util.Date

data class Document(
    val collection: String = "",
    val thumbnail_url: String,
    val image_url: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val display_sitename: String,
    val doc_url: String = "",
    val datetime: Date,
    var isHearted: Boolean = false
)