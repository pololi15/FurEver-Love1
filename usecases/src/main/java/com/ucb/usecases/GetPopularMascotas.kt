package com.ucb.usecases

import com.ucb.data.MascotaRepository
import com.ucb.data.NetworkResult
import com.ucb.domain.model.Mascota

class GetPopularMascotas(
    val mascotaRepository: MascotaRepository,
    val token: String
) {
    suspend fun invoke(): NetworkResult<List<Mascota>> {
        return mascotaRepository.getPopularMascotas(token = this.token)
    }
}


