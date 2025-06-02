package com.ucb.data.mascota

import com.ucb.domain.model.Mascota

interface IMascotaRepository {

    suspend fun agregarMascota(mascota: Mascota)
    suspend fun obtenerMascotas(): List<Mascota>
}

