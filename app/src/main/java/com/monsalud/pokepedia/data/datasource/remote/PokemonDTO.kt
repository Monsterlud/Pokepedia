package com.monsalud.pokepedia.data.datasource.remote

data class PokemonDTO(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val types: List<PokemonType>,
    val sprites: PokemonSprites,
    val stats: List<PokemonStat>,
)

data class PokemonType(val type: TypeDetails)
data class TypeDetails(val name: String)
data class PokemonSprites(val front_default: String)
data class PokemonStat(val base_stat: Int, val stat: StatDetails)
data class StatDetails(val name: String)

data class PokemonBaseDTO(
    val name: String?,
    val url: String?
)

data class PokemonBaseDTOList(
    var results: List<PokemonBaseDTO>
)
