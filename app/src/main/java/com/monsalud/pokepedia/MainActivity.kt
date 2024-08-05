package com.monsalud.pokepedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.monsalud.pokepedia.presentation.PokepediaTopAppBar
import com.monsalud.pokepedia.presentation.PokepediaViewModel
import com.monsalud.pokepedia.presentation.navigation.PokepediaNavigation
import com.monsalud.pokepedia.presentation.navigation.Screen
import com.monsalud.pokepedia.ui.theme.PokepediaTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : ComponentActivity() {
    private val viewModel: PokepediaViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PokepediaTheme {
                var showSearch by remember { mutableStateOf(false) }
                val filterText by viewModel.filterText.collectAsState()
                val currentScreen by viewModel.currentScreen.collectAsState()
//                val shouldNavigateBack by viewModel.shouldNavigateBack.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        PokepediaTopAppBar(
                            showSearchIcon = currentScreen == Screen.MainScreen,
                            showSearch = showSearch,
                            filterText = filterText,
                            onFilterChange = { viewModel.updateFilter(it) },
                            onSearchTriggered = {
                                viewModel.updateFilter(filterText)
//                                viewModel.clearFilter()
//                                showSearch = false
                            },
                            onToggleSearch = {
                                showSearch = !showSearch
                                Timber.d("Search Icon clicked. showSearch: $showSearch")
                                if (!showSearch) viewModel.clearFilter()
                            },
                            currentScreen = currentScreen.route,
                        )
                    }
                ) {
                    PokepediaNavigation(
                        innerPadding = it,
                        onScreenChange = { screen ->
                            viewModel.setCurrentScreen(screen)
                        },
                    )
                }
            }
        }
    }
}
