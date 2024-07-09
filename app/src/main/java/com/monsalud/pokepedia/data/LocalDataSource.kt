package com.monsalud.pokepedia.data

import com.monsalud.pokepedia.data.datasource.local.room.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun savePokemon(entity: PokemonEntity)

    suspend fun getPokemonList(pageSize: Int, offset: Int): Flow<List<PokemonEntity>>

    suspend fun clearDatabase(): Unit
}