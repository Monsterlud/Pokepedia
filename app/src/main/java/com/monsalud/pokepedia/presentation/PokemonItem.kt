package com.monsalud.pokepedia.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.monsalud.pokepedia.R
import com.monsalud.pokepedia.domain.Pokemon
import com.monsalud.pokepedia.ui.theme.pokemonPlaywriteLightFontFamily

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    modifier: Modifier,
    onPokemonClick: (Pokemon) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp)
            .fillMaxWidth()
            .clickable {
                onPokemonClick(pokemon)
            },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(

                ) {
                    Text(text = "Id: ",
                        color = MaterialTheme.colorScheme.onPrimary)
                    Text(
                        text = pokemon.id.toString(),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Row() {
                    Text(
                        text = "Pokemon Name: ",
                        fontFamily = pokemonPlaywriteLightFontFamily,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = pokemon.name,
                        fontWeight = FontWeight.Bold,
                        fontFamily = pokemonPlaywriteLightFontFamily,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "pokemon image",
                placeholder = null,
                error = painterResource(id = R.drawable.error_image),
                modifier = Modifier.size(70.dp).padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}