package com.monsalud.pokepedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.monsalud.pokepedia.presentation.PokepediaViewModel
import com.monsalud.pokepedia.presentation.navigation.PokepediaNavigation
import com.monsalud.pokepedia.ui.theme.PokepediaTheme
import com.monsalud.pokepedia.ui.theme.pokeDarkBlue
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

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                if (showSearch) {
                                    TextField(
                                        value = filterText,
                                        onValueChange = { viewModel.updateFilter(it) },
                                        placeholder = { Text("Search Pokemon") },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                        keyboardActions = KeyboardActions(onSearch = {
                                            // This will trigger when the user presses the search button on the keyboard
                                            viewModel.updateFilter(filterText)
                                            keyboardController?.hide()
                                        }),
                                        colors = TextFieldDefaults.textFieldColors(
                                            containerColor = Color.Transparent,
                                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                            focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(
                                                alpha = 0.6f
                                            ),
                                            cursorColor = MaterialTheme.colorScheme.onPrimary
                                        ),
)
                                } else {
                                    Text(
                                        text = getString(R.string.app_name),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = typography.titleLarge
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = pokeDarkBlue,
                            ),
                            actions = {
                                IconButton(
                                    onClick = {
                                        if (showSearch) {
                                            showSearch = false
                                            viewModel.clearFilter()
                                        } else {
                                            showSearch = true
                                        }
                                        Timber.d("Search Icon clicked. showSearch: $showSearch")
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (showSearch) Icons.Default.Close else Icons.Default.Search,
                                        contentDescription = if (showSearch) "Close search" else "Open search",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        )
                    }
                ) {
                    PokepediaNavigation(innerPadding = it)
                }
            }
        }
    }
}
