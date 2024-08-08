package com.monsalud.pokepedia.presentation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.monsalud.pokepedia.domain.Pokemon
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokemonDetailScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Composable
    fun TestablePokemonDetailScreen(pokemon: Pokemon) {
        PokemonDetailScreen(pokemon = pokemon, innerPadding = PaddingValues())
    }

    @Test
    fun testBasicPokemonInfoDisplayed() {
        val testPokemon = Pokemon(1, "Bulbasaur", 69, 7, "grass, dirt", "https://example.com/image.png", "HP: 45, Attack: 49")

        composeTestRule.setContent {
            TestablePokemonDetailScreen(pokemon = testPokemon)
        }

        composeTestRule.onRoot().printToLog("*****PokemonDetailScreenTest")

        composeTestRule.onRoot().assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithTag("titleText").assertIsDisplayed()
            .assertTextEquals("Pokemon Details")

        composeTestRule.onNodeWithTag("idLabel").assertIsDisplayed()
            .assertTextEquals("Id: ")
        composeTestRule.onNodeWithTag("idValue").assertIsDisplayed()
            .assertTextEquals("1")

        composeTestRule.onNodeWithTag("nameLabel").assertIsDisplayed()
            .assertTextEquals("Name: ")
        composeTestRule.onNodeWithTag("nameValue").assertIsDisplayed()
            .assertTextEquals("Bulbasaur")

        composeTestRule.onNodeWithTag("weightLabel").assertIsDisplayed()
            .assertTextEquals("Weight: ")
        composeTestRule.onNodeWithTag("weightValue").assertIsDisplayed()
            .assertTextEquals("69")

        composeTestRule.onNodeWithTag("heightLabel").assertIsDisplayed()
            .assertTextEquals("Height: ")
        composeTestRule.onNodeWithTag("heightValue").assertIsDisplayed()
            .assertTextEquals("7")

        composeTestRule.onNodeWithTag("typeLabel").assertIsDisplayed()
            .assertTextEquals("Types: ")
        composeTestRule.onNodeWithTag("typeValues").assertIsDisplayed()
            .assertTextEquals("grass, dirt")

        composeTestRule.onNodeWithTag("pokemonImage").assertIsDisplayed()
    }
}