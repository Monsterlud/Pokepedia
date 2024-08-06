package com.monsalud.pokepedia.data.datasource.utils

import com.monsalud.pokepedia.data.datasource.local.room.PokemonEntity
import com.monsalud.pokepedia.data.datasource.remote.PokemonDTO
import com.monsalud.pokepedia.domain.Pokemon

class EntityMappers : ObjectMappers<PokemonDTO, PokemonEntity> {

    override fun mapFromDtoToEntity(dto: PokemonDTO): PokemonEntity {
        // Convert the list of types to a comma-separated string
        val typesString = dto.types.joinToString(",") { it.type.name }

        // Convert the list of stats to a comma-separated string
        val statsString = dto.stats.joinToString(",") { it.stat.name }
        return PokemonEntity(
            id = dto.id,
            name = dto.name,
            weight = dto.weight,
            height = dto.height,
            types = typesString,
            image = dto.sprites.front_default,
            stats = statsString,
        )
    }

    override fun mapFromEntityToPokemon(entity: PokemonEntity): Pokemon {
        val typesList = entity.types.split(",")
        val statsList = entity.stats.split(",")

        return Pokemon(
            id = entity.id,
            name = entity.name,
            weight = entity.weight,
            height = entity.height,
            type = typesList.joinToString(", "),
            image = entity.image,
            stats = statsList.joinToString(", "),
        )
    }

    override fun mapFromPokemonoToEntity(pokemon: Pokemon): PokemonEntity {
        // Convert the list of types and stats to comma-separated strings
        val typesString = pokemon.type.split(", ").joinToString(",")
        val statsString = pokemon.stats.split(", ").joinToString(",")

        return PokemonEntity(
            id = pokemon.id,
            name = pokemon.name,
            weight = pokemon.weight,
            height = pokemon.height,
            types = typesString,
            image = pokemon.image,
            stats = statsString,
        )
    }

}