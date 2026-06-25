package com.example.princesa_disney.apiExterna

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient private constructor() {
    companion object {
        private lateinit var instance: Retrofit

        fun getRetrofitInstance(): Retrofit {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    val httpClient = OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .build()
                    instance = Retrofit.Builder()
                        .client(httpClient)
                        .baseUrl("https://api.jikan.moe/v4/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return instance
            }
        }

        fun createAnimeApiService(): AnimeService =
            getRetrofitInstance().create(AnimeService::class.java)
    }
}