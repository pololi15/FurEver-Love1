package com.ucb.framework.mappers
import com.ucb.domain.model.Mascota
import com.ucb.framework.dto.mascotaDto


fun mascotaDto.toModel(): Mascota {
    return Mascota(
        nombre = nombre,
        overview = overview,
        raza = posterPath
    )
}
