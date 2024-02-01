package com.nhkim.imagecollector.data.image

data class ImageResponse(
    val meta: Meta,
    val documents: List<Document>
)