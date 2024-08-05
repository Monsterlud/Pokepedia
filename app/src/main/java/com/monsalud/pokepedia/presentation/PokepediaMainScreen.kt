package com.monsalud.pokepedia.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.monsalud.pokepedia.presentation.navigation.Screen
import kotlinx.coroutines.delay
import timber.log.Timber

@Composable
fun PokepediaMainScreen(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: PokepediaViewModel,
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val pokemonListState by viewModel.filteredPokemon.collectAsStateWithLifecycle(emptyList())
    val filterText by viewModel.filterText.collectAsStateWithLifecycle()

    var recomposeCounter by remember { mutableStateOf(0) }

    LaunchedEffect(pokemonListState) {
        Timber.d("****pokemonListState updated in the screen composable. Size: ${pokemonListState.size}")
    }

    LaunchedEffect(filterText) {
        Timber.d("****filterText updated in the screen composable. Value: $filterText")
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            recomposeCounter++
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("PokemonList"),
            contentPadding = PaddingValues(0.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            state = remember { LazyListState() }
        ) {
            items(items = pokemonListState, key = { pokemon -> pokemon.id }) { pokemon ->
                PokemonItem(
                    pokemon = pokemon,
                    modifier = Modifier.testTag("PokemonItem")
                ) { clickedPokemon ->
                        navController.navigate(Screen.DetailScreen.createRoute(clickedPokemon))
                    }
                }
            item {
                if (!isLoading && pokemonListState.isNotEmpty()) {
                    LaunchedEffect(Unit) {
                        viewModel.loadNextPage()
                    }
                }
            }
        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
                    .testTag("LoadingIndicator")
            )
        }
    }
}
