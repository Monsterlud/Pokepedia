package com.monsalud.pokepedia.data.datasource.remote

import com.monsalud.pokepedia.data.RemoteDataSource
import com.monsalud.pokepedia.data.datasource.remote.NetworkConstants.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException

class RemoteDataSourceImpl(
    private val client: HttpClient
) : RemoteDataSource {

    override suspend fun getPokemonFromApi(
        limit: Int,
        offset: Int
    ): Result<String?> {
        try {
            val pokemonString =
                client.get<String>("$BASE_URL/pokemon") {
                    contentType(Json)
                    parameter("limit", limit)
                    parameter("offset", offset)
                }
            return Result.success(pokemonString)
        } catch (e: IOException) {
            return Result.failure(DataError.Network(e))
        } catch (e: Exception) {
            return Result.failure(DataError.Unknown(e))
        }
    }

    override suspend fun getPokemonDetailsFromApi(name: String): Result<String> {
        try {
            val pokemonString =
                client.get<String>("$BASE_URL/pokemon/$name") {
                    contentType(Json)
                }
            return Result.success(pokemonString)
        } catch (e: IOException) {
            return Result.failure(DataError.Network(e))
        } catch (e: Exception) {
            return Result.failure(DataError.Unknown(e))
        }
    }
}