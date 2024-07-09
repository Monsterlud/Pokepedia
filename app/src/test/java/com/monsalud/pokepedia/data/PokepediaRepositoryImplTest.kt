package com.monsalud.pokepedia.data

import com.google.gson.Gson
import com.monsalud.pokepedia.data.datasource.local.room.PokemonEntity
import com.monsalud.pokepedia.data.datasource.remote.PokemonBaseDTO
import com.monsalud.pokepedia.data.datasource.remote.PokemonBaseDTOList
import com.monsalud.pokepedia.data.datasource.remote.PokemonDTO
import com.monsalud.pokepedia.data.datasource.utils.EntityMappers
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PokepediaRepositoryImplTest {

    private val localDataSource: LocalDataSource = mockk()
    private val remoteDataSource: RemoteDataSource = mockk()
    private val gson: Gson = mockk()
    private val entityMappers: EntityMappers = mockk()

    private lateinit var repository: PokepediaRepositoryImpl

    @Before
    fun setUp() {
        repository = PokepediaRepositoryImpl(
            localDataSource,
            remoteDataSource,
            gson,
            entityMappers
        )
    }

    @Test
    fun `retrievePokemonListFromLocalStorage should return a Flow of PokemonEntity list`(): Unit = runTest {
        val pokemonList = listOf(
            PokemonEntity(1, "Pikachu"),
            PokemonEntity(2, "Charmander")
        )
        coEvery { localDataSource.getPokemonList(any(), any()) } returns flowOf(pokemonList)

        val result = repository.retrievePokemonListFromLocalStorage(10, 0)

        assertEquals(pokemonList, result.first())
    }

    @Test
    fun `getPokemonListJsonFromAPI should return a Success result with a JSON string`(): Unit = runTest {
        val jsonString = "{\"results\": []}"
        coEvery { remoteDataSource.getPokemonFromApi(any(), any()) } returns Result.success(jsonString)

        val result = repository.getPokemonListJsonFromAPI(10, 0)
        assertEquals(Result.success(jsonString), result)
    }

    @Test
    fun `getPokemonListJsonFromAPI should return Failure result on Exception`() = runTest {
        val exception = Exception("Network Error")
        coEvery { remoteDataSource.getPokemonFromApi(any(), any()) } returns Result.failure(exception)

        val result = repository.getPokemonListJsonFromAPI(10, 0)
        assert(result.isFailure)
    }

    @Test
    fun `getAndSavePokepediaList should fetch, map, and save PokemonEntity`() = runTest {
        val pokemonBaseDTOList = listOf(
            PokemonBaseDTO("Pikachu", "url"),
            PokemonBaseDTO("Charmander", "url")
        )
        val pokemonBaseDTOListJson = """[{"name":"Pikachu","url":"url"},{"name":"Charmander","url":"url"}]"""
        val pokemonJson = """{"results": $pokemonBaseDTOListJson}"""
        val detailedPokemonJson = """{"id": 1, "name": "Bulbasaur", "weight": 1, "height": 1, "types": [{"type": {"name": "grass"}}], "sprites": {"front_default": "image_url"}, "stats": []}"""
        val pokemonDTO = mockk<PokemonDTO>()
        val pokemonEntity = PokemonEntity(1, "Bulbasaur", 1, 1, "grass", "image_url", "stats")

        // Mock gson.toJson() for the specific input
        every { gson.toJson(pokemonBaseDTOList) } returns pokemonBaseDTOListJson

        // Mock gson.fromJson() for PokemonBaseDTOList
        every { gson.fromJson(pokemonJson, PokemonBaseDTOList::class.java) } returns PokemonBaseDTOList(pokemonBaseDTOList)

        // Mock gson.fromJson() for PokemonDTO
        every { gson.fromJson(detailedPokemonJson, PokemonDTO::class.java) } returns pokemonDTO

        coEvery { remoteDataSource.getPokemonFromApi(any(), any()) } returns Result.success(pokemonJson)
        coEvery { remoteDataSource.getPokemonDetailsFromApi(any()) } returns Result.success(detailedPokemonJson)
        every { entityMappers.mapFromDtoToEntity(pokemonDTO) } returns pokemonEntity
        coEvery { localDataSource.savePokemon(pokemonEntity) } just Runs

        val result = repository.getAndSavePokepediaList(10, 0)

        assertTrue(result.isSuccess)
        coVerify(exactly = pokemonBaseDTOList.size) { localDataSource.savePokemon(pokemonEntity) }
    }
}