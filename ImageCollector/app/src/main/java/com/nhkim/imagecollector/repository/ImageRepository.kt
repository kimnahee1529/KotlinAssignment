package com.nhkim.imagecollector.repository

import android.util.Log
import com.nhkim.imagecollector.data.image.ImageResponse
import com.nhkim.imagecollector.data.video.VideoResponse
import com.nhkim.imagecollector.network.NetWorkClient

class ImageRepository {
    suspend fun searchImage(search: String): ImageResponse {
        Log.d("ImageRepository", "searchImage")
        val authKey = "KakaoAK ${NetWorkClient.apiKey}"
        return NetWorkClient.imageNetWork.getThumbnailImage(authKey, search, size = 80)
    }
    suspend fun searchVideo(search: String): VideoResponse {
        Log.d("ImageRepository", "searchVideo")
        val authKey = "KakaoAK ${NetWorkClient.apiKey}"
        return NetWorkClient.videoNetWork.getThumbnailVideo(authKey, search)
    }
}