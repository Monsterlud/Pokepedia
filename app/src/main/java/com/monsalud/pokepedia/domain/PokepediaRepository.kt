package com.monsalud.pokepedia.domain

import com.monsalud.pokepedia.data.datasource.local.room.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface PokepediaRepository {

    /**
     * Local Data from Room
     */
    suspend fun retrievePokemonListFromLocalStorage(
        pageSize: Int,
        offset: Int,
    ): Flow<List<PokemonEntity>>

    /**
     * Remote Data from APIs
     */
    suspend fun getPokemonListJsonFromAPI(
        pageSize: Int,
        offset: Int,
    ) : Result<String?>

    /**
     * Get Pokemon List from API and save to Local Storage
     */
    suspend fun getAndSavePokepediaList(
        pageSize: Int,
        offset: Int,
    ) : Result<String>

}