package com.nhkim.imagecollector.network

import com.nhkim.imagecollector.data.video.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface VideoNetworkInterface {
    @GET("v2/search/vclip")
    suspend fun getThumbnailVideo(
        @Header("Authorization") authorization: String,
        @Query("query") query: String,
        @Query("sort") sort: String? = "recency", // 선택적 파라미터는 기본값을 null로 설정할 수 있습니다.
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): VideoResponse
}