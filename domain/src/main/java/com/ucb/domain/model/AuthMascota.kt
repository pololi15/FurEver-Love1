package com.ucb.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Mascota(
    val nombre: String,
    val raza: String,
    val overview: String
)