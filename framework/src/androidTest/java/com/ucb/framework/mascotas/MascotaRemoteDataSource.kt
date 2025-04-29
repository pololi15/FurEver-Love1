package com.ucb.framework.mascotas


import com.ucb.data.NetworkResult
import com.ucb.data.mascota.IMascotaRemoteDataSource
import com.ucb.domain.model.Mascota
import com.ucb.framework.sevice.RetrofitBuilder
import com.ucb.framework.mappers.toModel


class MascotaRemoteDataSource(val retrofiService: RetrofitBuilder): IMascotaRemoteDataSource {

    override suspend fun fetchPopularMascotas(token: String): NetworkResult<List<Mascota>> {
        val response = retrofiService.mascotaService.fetchPopularMascota(token)
        if (response.isSuccessful) {
            return NetworkResult.Success(response.body()!!.results.map { it.toModel() })
        } else {
            return NetworkResult.Error(response.message())
        }
    }

}

