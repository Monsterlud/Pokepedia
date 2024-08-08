package com.monsalud.pokepedia.data.datasource.utils

import com.monsalud.pokepedia.data.datasource.local.room.PokemonEntity
import com.monsalud.pokepedia.data.datasource.remote.PokemonDTO
import com.monsalud.pokepedia.domain.Pokemon

class EntityMappers : ObjectMappers<PokemonDTO, PokemonEntity> {

    override fun mapFromDtoToEntity(dto: PokemonDTO): PokemonEntity {
        // Convert the list of types to a comma-separated string
        val typesString = dto.types.joinToString(", ") { it.type.name }

        // Combine the stats name and value into a comma-separated string
        val combinedStatsString = dto.stats.joinToString(", ") { "${it.stat.name}: ${it.base_stat}" }

        return PokemonEntity(
            id = dto.id,
            name = dto.name,
            weight = dto.weight,
            height = dto.height,
            types = typesString,
            image = dto.sprites.front_default,
            stats = combinedStatsString,
        )
    }

    override fun mapFromEntityToPokemon(entity: PokemonEntity): Pokemon {

        return Pokemon(
            id = entity.id,
            name = entity.name,
            weight = entity.weight,
            height = entity.height,
            types = entity.types,
            image = entity.image,
            stats = entity.stats,
        )
    }
}