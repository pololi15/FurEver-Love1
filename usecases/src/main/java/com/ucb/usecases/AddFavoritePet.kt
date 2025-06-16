package com.ucb.usecases

import com.ucb.data.mascota.IMascotaRepository
import com.ucb.domain.model.Mascota

class AddFavoritePet(private val repository: IMascotaRepository) {
    suspend operator fun invoke(uid: String, mascota: Mascota) {
        repository.agregarFavorito(uid, mascota)
    }
}
