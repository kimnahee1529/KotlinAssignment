package com.nhkim.imagecollector.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetWorkClient {
    private const val IMAGE_BASE_URL = "https://dapi.kakao.com/"
    private const val VIDEO_BASE_URL = "https://dapi.kakao.com/"

    const val apiKey = "de30213ec057d34a9929a3910e5b9566"
    private fun createOkHttpClient(): OkHttpClient {
        val intercepter = HttpLoggingInterceptor()

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(intercepter)
            .build()
    }

    private val imageRetrofit = Retrofit.Builder()
        .baseUrl(IMAGE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()

    val imageNetWork: ImageNetworkInterface = imageRetrofit.create(ImageNetworkInterface::class.java)

    private val videoRetrofit = Retrofit.Builder()
        .baseUrl(VIDEO_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()

    val videoNetWork: VideoNetworkInterface = videoRetrofit.create(VideoNetworkInterface::class.java)
}