package com.ucb.framework.mappers

import com.ucb.domain.model.Mascota
import com.ucb.framework.dto.MascotaDTO

fun MascotaDTO.toDomain(): Mascota {
    return Mascota(
        nombre = nombre,
        edad = edad,
        especie = especie,
        ubicacion = ubicacion,
        fotoUrl = fotoUrl,
        id = ""
    )
}
fun Mascota.toEntity(): Mascota {
    return Mascota(
        nombre = nombre,
        edad = edad,
        especie = especie,
        ubicacion = ubicacion,
        fotoUrl = fotoUrl
    )
}

// Interesante, Firestore genera automaticamente el id cuando haces collection.add()
// pero cuando guardas una mascota no tiene id aun