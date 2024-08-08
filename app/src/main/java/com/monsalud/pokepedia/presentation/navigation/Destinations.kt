package com.monsalud.pokepedia.presentation.navigation

import android.net.Uri
import com.monsalud.pokepedia.domain.Pokemon

sealed class Screen(val route: String) {
    data object MainScreen : Screen("main")
    data object DetailScreen : Screen("detail/{pokemonId}/{pokemonName}/{pokemonWeight}/{pokemonHeight}/{pokemonType}/{pokemonImageEncoded}/{pokemonStatsEncoded}") {
        fun createRoute(pokemon: Pokemon): String {
            val encodedImage = Uri.encode(pokemon.image)
            val encodedStats = Uri.encode(pokemon.stats)
            return "detail/${pokemon.id}/${pokemon.name}/${pokemon.weight}/${pokemon.height}/${pokemon.types}/$encodedImage/$encodedStats"
        }
    }
}