package com.monsalud.pokepedia.domain

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val types: String,
    val image: String,
    val stats: String,
)