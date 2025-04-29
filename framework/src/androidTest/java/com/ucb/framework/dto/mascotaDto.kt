package com.ucb.framework.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class mascotaDto(
    val nombre: String,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String
)