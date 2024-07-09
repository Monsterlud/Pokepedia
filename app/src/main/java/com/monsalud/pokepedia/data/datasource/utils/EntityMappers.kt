package com.monsalud.pokepedia.data.datasource.utils

import com.monsalud.pokepedia.data.datasource.local.room.PokemonEntity
import com.monsalud.pokepedia.data.datasource.remote.PokemonDTO
import com.monsalud.pokepedia.domain.Pokemon

class EntityMappers : ObjectMappers<PokemonDTO, PokemonEntity> {

    override fun mapFromDtoToEntity(dto: PokemonDTO): PokemonEntity {
        return PokemonEntity(
            id = dto.id,
            name = dto.name,
            weight = dto.weight,
            height = dto.height,
            type = dto.types[0].type.name,
            image = dto.sprites.front_default,
            stats = dto.stats[0].stat.name,
        )
    }

    override fun mapFromEntityToPokemon(entity: PokemonEntity): Pokemon {
        return Pokemon(
            id = entity.id,
            name = entity.name,
            weight = entity.weight,
            height = entity.height,
            type = entity.type,
            image = entity.image,
            stats = entity.stats,
        )
    }

    override fun mapFromPokemonoToEntity(pokemon: Pokemon): PokemonEntity {
        return PokemonEntity(
            id = pokemon.id,
            name = pokemon.name,
            weight = pokemon.weight,
            height = pokemon.height,
            type = pokemon.type,
            image = pokemon.image,
            stats = pokemon.stats,
        )
    }

}