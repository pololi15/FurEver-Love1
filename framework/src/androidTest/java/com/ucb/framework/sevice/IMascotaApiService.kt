package com.ucb.framework.sevice

import com.ucb.framework.dto.MascotaResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IMascotaApiService {
    @GET("/3/discover/movie?sort_by=popularity.desc")
    suspend fun fetchPopularMascota(@Query("api_key") token: String): Response<MascotaResponseDto>
}