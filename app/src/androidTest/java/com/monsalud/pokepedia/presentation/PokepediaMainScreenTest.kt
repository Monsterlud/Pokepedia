package com.monsalud.pokepedia.presentation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.monsalud.pokepedia.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokepediaMainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testInitialLoadingState() {
        // Check that the PokemonList exists (it always does, even if empty)
        composeTestRule.onNodeWithTag("PokemonList").assertExists()

        // Check that the LoadingIndicator is displayed initially
        composeTestRule.onNodeWithTag("LoadingIndicator").assertExists()

        // Check that no PokemonItems exist yet
        composeTestRule.onNodeWithTag("PokemonItem").assertDoesNotExist()
    }
}