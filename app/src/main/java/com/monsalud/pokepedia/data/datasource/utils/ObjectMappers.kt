package com.monsalud.pokepedia.data.datasource.utils

import com.monsalud.pokepedia.data.datasource.local.room.PokemonEntity
import com.monsalud.pokepedia.data.datasource.remote.PokemonDTO
import com.monsalud.pokepedia.domain.Pokemon

interface ObjectMappers<PokemonDTO, PokemonEntity> {

    fun mapFromDtoToEntity(dto: PokemonDTO) : PokemonEntity

    fun mapFromEntityToPokemon(entity: PokemonEntity) : Pokemon

}