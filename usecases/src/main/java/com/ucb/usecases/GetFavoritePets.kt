package com.ucb.usecases

import com.ucb.data.mascota.IMascotaRepository
import com.ucb.domain.model.Mascota

class GetFavoritePets(private val repository: IMascotaRepository) {
    suspend operator fun invoke(uid: String): List<Mascota> {
        return repository.obtenerFavoritos(uid)
    }
}
