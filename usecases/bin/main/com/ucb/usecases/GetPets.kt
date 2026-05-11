package com.ucb.usecases

import com.ucb.data.mascota.IMascotaRepository


class GetPets (private val repository: IMascotaRepository){
    suspend fun invoke() = repository.obtenerMascotas()
}