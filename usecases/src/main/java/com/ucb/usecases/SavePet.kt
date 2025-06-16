package com.ucb.usecases

import com.ucb.data.mascota.IMascotaRepository
import com.ucb.domain.model.Mascota


class SavePet(private val repository: IMascotaRepository) {
    suspend operator fun invoke(pet: Mascota) = repository.agregarMascota(pet)
}