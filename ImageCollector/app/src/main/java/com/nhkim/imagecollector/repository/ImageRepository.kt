package com.nhkim.imagecollector.repository

import android.util.Log
import com.nhkim.imagecollector.data.ImageResponse
import com.nhkim.imagecollector.network.NetWorkClient

class ImageRepository {
    suspend fun searchImage(search: String): ImageResponse {
        Log.d("ImageRepository", "searchImage")
        val authKey = "KakaoAK ${NetWorkClient.apiKey}"
        return NetWorkClient.imageNetWork.getThumbnailImage(authKey, search, size = 80)
    }
}