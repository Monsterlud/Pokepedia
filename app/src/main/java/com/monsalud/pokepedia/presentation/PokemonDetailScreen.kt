package com.monsalud.pokepedia.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.monsalud.pokepedia.R
import com.monsalud.pokepedia.domain.Pokemon
import com.monsalud.pokepedia.ui.theme.pokemonPlaywriteLightFontFamily
import timber.log.Timber

@Composable
fun PokemonDetailScreen(
    pokemon: Pokemon,
    innerPadding: PaddingValues
) {
    Timber.d("Pokemon received: name = ${pokemon.name}, type = ${pokemon.type}")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(innerPadding)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Pokemon Details",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = pokemonPlaywriteLightFontFamily,
                modifier = Modifier.testTag("titleText")
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = "Id: ",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("idLabel")
                )
                Text(
                    text = pokemon.id.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("idValue")
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = "Name: ",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("nameLabel")
                )
                Text(
                    text = pokemon.name,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("nameValue")
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.tertiary,
                thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = "Weight: ",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("weightLabel")
                )
                Text(
                    text = pokemon.weight.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("weightValue")
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = "Height: ",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("heightLabel")
                )
                Text(
                    text = pokemon.height.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("heightValue")
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = "Type: ",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("typeLabel")
                )
                Text(
                    text = pokemon.type,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("typeValue")
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = "stats: ",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("statsLabel")
                )
                Text(
                    text = pokemon.stats,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("statsValue")
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "pokemon image",
                placeholder = null,
                error = painterResource(id = R.drawable.error_image),
                modifier = Modifier.size(180.dp).padding(end = 16.dp).testTag("pokemonImage"),
                contentScale = ContentScale.Crop
            )
        }
    }
}
