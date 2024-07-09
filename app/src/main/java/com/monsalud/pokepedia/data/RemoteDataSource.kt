package com.monsalud.pokepedia.data

interface RemoteDataSource {

    suspend fun getPokemonFromApi(
        limit: Int,
        offset: Int,
    ) : Result<String?>

    suspend fun getPokemonDetailsFromApi(
        name: String,
    ) : Result<String?>
}