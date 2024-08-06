package com.monsalud.pokepedia.data.datasource.local

import com.google.gson.Gson
import com.monsalud.pokepedia.data.LocalDataSource
import com.monsalud.pokepedia.data.datasource.local.room.PokemonEntity
import com.monsalud.pokepedia.data.datasource.local.room.PokepediaDAO
import com.monsalud.pokepedia.data.datasource.utils.EntityMappers
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class LocalDataSourceImpl(
    private val pokepediaDAO: PokepediaDAO,
    private val gson: Gson,
    private val mapper: EntityMappers
) : LocalDataSource {

    override suspend fun savePokemon(entity: PokemonEntity) {
        Timber.d("***Entity saving to Room Database: ${entity.id}, ${entity.name}, ${entity.weight}, ${entity.height}, ${entity.types}, ${entity.image}, ${entity.stats}")
        pokepediaDAO.addPokemonToRoom(entity)
    }

    override suspend fun getPokemonList(pageSize: Int, offset: Int): Flow<List<PokemonEntity>> {
        return pokepediaDAO.getPokemonListFromRoom(pageSize, offset)
    }

    override suspend fun clearDatabase() {
        pokepediaDAO.clearPokepediaDatabase()
    }
}
