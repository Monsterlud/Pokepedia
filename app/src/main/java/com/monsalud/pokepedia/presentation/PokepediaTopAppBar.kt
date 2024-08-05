package com.monsalud.pokepedia.presentation

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.monsalud.pokepedia.R
import com.monsalud.pokepedia.ui.theme.pokeDarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokepediaTopAppBar(
    showSearchIcon: Boolean,
    showSearch: Boolean,
    filterText: String,
    onFilterChange: (String) -> Unit,
    onSearchTriggered: (String) -> Unit, // Callback for search action
    onToggleSearch: () -> Unit, // Callback to toggle search
    currentScreen: String,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    TopAppBar(
        title = {
            if (showSearch && currentScreen == "main") {
                TextField(
                    value = filterText,
                    onValueChange = onFilterChange,
                    placeholder = { Text("Search Pokemon") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        onSearchTriggered(filterText)
                        keyboardController?.hide()
                    }),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                        cursorColor = MaterialTheme.colorScheme.onPrimary
                    ),
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            } else {
                Text(
                    text = stringResource(R.string.app_name),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = typography.titleLarge
                )
            }
        },
//        navigationIcon = {
//            if (currentScreen != "main") {
//                IconButton(onClick = { viewModel.updateShouldNavigateBack(true) }) {
//                    Icon(
//                        imageVector = Icons.Filled.ArrowBack,
//                        contentDescription = "Back"
//                    )
//                }
//            }
//        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = pokeDarkBlue),
        actions = {
            if (showSearchIcon) {
                IconButton(
                    onClick = onToggleSearch // Use the callback
                ) {
                    Icon(
                        imageVector = if (showSearch) Icons.Default.Close else Icons.Default.Search,
                        contentDescription = if (showSearch) "Close search" else "Open search",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}