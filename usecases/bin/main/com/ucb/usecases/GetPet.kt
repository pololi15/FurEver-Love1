package com.ucb.usecases

import com.ucb.data.mascota.IMascotaRepository

class GetPet (private val repository: IMascotaRepository){
    suspend fun invoke(id: String) = repository.obtenerMascota(id)
}