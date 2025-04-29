package com.ucb.data.mascota

import com.ucb.data.NetworkResult
import com.ucb.domain.model.Mascota

interface IMascotaRemoteDataSource {
    suspend fun fetchPopularMascotas(token: String): NetworkResult<List<Mascota>>
}