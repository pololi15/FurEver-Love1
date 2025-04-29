package com.ucb.framework.sevice

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitBuilder(
    val context: Context
) {

    private fun getRetrofit(url: String): Retrofit {

        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(url)
            .client(client)
            .build()
    }
    val apiService: IApiService = getRetrofit(BASE_URL).create(IApiService::class.java)
    val mascotaService: IMascotaApiService = getRetrofit(BASE_URL_MOVIES).create(IMascotaApiService::class.java)

    companion object {
        private const val BASE_URL = "https://api.github.com"
        private const val BASE_URL_MOVIES = "https://api.themoviedb.org"
    }
}
