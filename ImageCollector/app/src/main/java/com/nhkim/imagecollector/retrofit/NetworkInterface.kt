package com.nhkim.imagecollector.retrofit

import android.media.Image
import com.nhkim.imagecollector.data.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetworkInterface {
    @GET("v2/search/image")
    suspend fun getThumbnailImage(
        @Header("Authorization") authorization: String,
        @Query("query") query: String,
        @Query("sort") sort: String? = null, // 선택적 파라미터는 기본값을 null로 설정할 수 있습니다.
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): ImageResponse
}