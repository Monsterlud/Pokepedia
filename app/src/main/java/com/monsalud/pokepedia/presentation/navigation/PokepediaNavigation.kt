package com.monsalud.pokepedia.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.monsalud.pokepedia.domain.Pokemon
import com.monsalud.pokepedia.presentation.PokemonDetailScreen
import com.monsalud.pokepedia.presentation.PokepediaMainScreen
import com.monsalud.pokepedia.presentation.PokepediaViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PokepediaNavigation(
    innerPadding: PaddingValues,
    onScreenChange: (Screen) -> Unit,
) {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.primary
    val viewModel: PokepediaViewModel = getViewModel()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = false
        )
    }

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            PokepediaMainScreen(
                navController = navController,
                innerPadding = innerPadding,
                viewModel = viewModel,
            )
            onScreenChange(Screen.MainScreen)
        }
        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(
                navArgument("pokemonId") { type = NavType.IntType },
                navArgument("pokemonName") { type = NavType.StringType },
                navArgument("pokemonWeight") { type = NavType.IntType },
                navArgument("pokemonHeight") { type = NavType.IntType },
                navArgument("pokemonType") { type = NavType.StringType },
                navArgument("pokemonImageEncoded") { type = NavType.StringType },
                navArgument("pokemonStatsEncoded") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("pokemonId") ?: -1
            val name = backStackEntry.arguments?.getString("pokemonName") ?: "Unknown Name"
            val weight = backStackEntry.arguments?.getInt("pokemonWeight") ?: -1
            val height = backStackEntry.arguments?.getInt("pokemonHeight") ?: -1
            val type = backStackEntry.arguments?.getString("pokemonType") ?: "Unknown Type"

            val imageEncoded = backStackEntry.arguments?.getString("pokemonImageEncoded") ?: "Unknown Image URL"
            val statsEncoded = backStackEntry.arguments?.getString("pokemonStatsEncoded") ?: "Unknown Stats"
            val image = Uri.decode(imageEncoded)
            val stats = Uri.decode(statsEncoded)
            PokemonDetailScreen(Pokemon(id, name, weight, height, type, image, stats), innerPadding)
            onScreenChange(Screen.DetailScreen)
        }
    }
}
