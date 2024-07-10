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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.monsalud.pokepedia.R
import com.monsalud.pokepedia.ui.theme.pokeDarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokepediaTopAppBar(
    showSearch: Boolean,
    filterText: String,
    onFilterChange: (String) -> Unit,
    onSearchTriggered: () -> Unit, // Callback for search action
    onToggleSearch: () -> Unit, // Callback to toggle search
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TopAppBar(
        title = {
            if (showSearch) {
                TextField(
                    value = filterText,
                    onValueChange = onFilterChange,
                    placeholder = { Text("Search Pokemon") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        onSearchTriggered() // Trigger the search
                        keyboardController?.hide()
                    }),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                        cursorColor = MaterialTheme.colorScheme.onPrimary
                    ),
                )
            } else {
                Text(
                    text = stringResource(R.string.app_name),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = typography.titleLarge
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = pokeDarkBlue),
        actions = {
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
    )
}