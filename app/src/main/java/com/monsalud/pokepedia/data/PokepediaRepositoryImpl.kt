package com.monsalud.pokepedia.data

import com.google.gson.Gson
import com.monsalud.pokepedia.data.datasource.local.room.PokemonEntity
import com.monsalud.pokepedia.data.datasource.remote.PokemonBaseDTO
import com.monsalud.pokepedia.data.datasource.remote.PokemonBaseDTOList
import com.monsalud.pokepedia.data.datasource.remote.PokemonDTO
import com.monsalud.pokepedia.data.datasource.utils.EntityMappers
import com.monsalud.pokepedia.domain.PokepediaRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class PokepediaRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val gson: Gson,
    private val entityMapper: EntityMappers
) : PokepediaRepository {

    override suspend fun retrievePokemonListFromLocalStorage(pageSize: Int, offset: Int): Flow<List<PokemonEntity>> {
        return localDataSource.getPokemonList(pageSize, offset)
    }

    override suspend fun getPokemonListJsonFromAPI(pageSize: Int, offset: Int): Result<String?> {
        return remoteDataSource.getPokemonFromApi(pageSize, offset)
    }

    override suspend fun getAndSavePokepediaList(
        pageSize: Int,
        offset: Int,
    ): Result<String> {

        val pokemonBaseDTOList = getBaseDTOPokemonList(pageSize, offset)

        pokemonBaseDTOList.map { baseDTO ->
            val detailedResult = baseDTO.name?.let {
                remoteDataSource.getPokemonDetailsFromApi(baseDTO.name)
            }

            if (detailedResult?.isSuccess == true) {
                val detailedPokemonJson = detailedResult.getOrNull()
                val detailedPokemonDTO =
                    gson.fromJson(detailedPokemonJson, PokemonDTO::class.java)
                Timber.d("***detailedPokemonDTO: $detailedPokemonDTO")
                val pokemonEntity = entityMapper.mapFromDtoToEntity(detailedPokemonDTO)
                Timber.d("***pokemonEntity: ${pokemonEntity.id}, ${pokemonEntity.name}, ${pokemonEntity.weight}, ${pokemonEntity.height}, ${pokemonEntity.type}, ${pokemonEntity.image}, ${pokemonEntity.stats}")
                localDataSource.savePokemon(pokemonEntity)
            }
        }
        return Result.success("Success")
    }

    private suspend fun getBaseDTOPokemonList(
        pageSize: Int,
        offset: Int,
    ): List<PokemonBaseDTO> {
        val baseListResult = getPokemonListJsonFromAPI(pageSize,offset)

        if (baseListResult.isFailure) {
            Timber.e("Error getting pokemon list: ${baseListResult.exceptionOrNull()?.message}")
            return emptyList()
        }

        val pokemonJson = baseListResult.getOrNull()
        val pokemonBaseDTOList = gson.fromJson(pokemonJson, PokemonBaseDTOList::class.java)

        return pokemonBaseDTOList.results
    }
}