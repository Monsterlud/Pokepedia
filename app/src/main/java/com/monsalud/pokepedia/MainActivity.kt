package com.monsalud.pokepedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.monsalud.pokepedia.presentation.PokepediaTopAppBar
import com.monsalud.pokepedia.presentation.PokepediaViewModel
import com.monsalud.pokepedia.presentation.navigation.PokepediaNavigation
import com.monsalud.pokepedia.presentation.navigation.Screen
import com.monsalud.pokepedia.ui.theme.PokepediaTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : ComponentActivity() {
    private val viewModel: PokepediaViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PokepediaTheme {
                var showSearch by remember { mutableStateOf(false) }
                val filterText by viewModel.filterText.collectAsState()
                val keyboardController = LocalSoftwareKeyboardController.current
                val currentScreen by viewModel.currentScreen.collectAsState()

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
                                showSearch = false // Optionally close search after triggering
                            },
                            onToggleSearch = {
                                showSearch = !showSearch
                                Timber.d("Search Icon clicked. showSearch: $showSearch")
                                if (!showSearch) viewModel.clearFilter()
                            }
                        )
                    }
                ) {
                    PokepediaNavigation(
                        innerPadding = it,
                        onScreenChange = { screen ->
                            viewModel.setCurrentScreen(screen)
                        })
                }
            }
        }
    }
}
