package com.monsalud.pokepedia.data.datasource.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokepediaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonToRoom(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemon_table ORDER BY id LIMIT :limit OFFSET :offset")
    fun getPokemonListFromRoom(limit: Int, offset: Int): Flow<List<PokemonEntity>>

    @Query("DELETE FROM pokemon_table")
    suspend fun clearPokepediaDatabase()
}