package com.example.princesa_disney.apiExterna

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeService {
    @GET("anime")
    fun buscarAnimes(
        @Query("q") busca: String,
        @Query("limit") limite: Int = 15
    ): Call<JikanResponse>
}