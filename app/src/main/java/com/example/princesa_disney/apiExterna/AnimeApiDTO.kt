package com.example.princesa_disney.apiExterna

import com.google.gson.annotations.SerializedName

data class JikanResponse(
    @SerializedName("data") val data: List<AnimeApiDto>
)

data class AnimeApiDto (
    @SerializedName("mal_id") val malId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("synopsis") val synopsis: String?,
    @SerializedName("episodes") val episodes: Int?,
    @SerializedName("score") val score: Double?,
    @SerializedName("images") val images: ImagesDto
)


data class ImagesDto(@SerializedName("jpg") val jpg: ImageUrlDto)
data class ImageUrlDto(@SerializedName("image_url") val imageUrl: String)