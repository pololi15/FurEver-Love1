package com.ucb.data

import com.ucb.data.mascota.IMascotaRemoteDataSource
import com.ucb.domain.model.Mascota


class MascotaRepository(
    val remoteDataSource: IMascotaRemoteDataSource
) {

    suspend fun getPopularMascotas(token: String): NetworkResult<List<Mascota>> {
        return this.remoteDataSource.fetchPopularMascotas(token)
    }
}