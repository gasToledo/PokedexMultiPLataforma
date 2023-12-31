package com.example.pokedexmultip.android.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokedexmultip.android.R
import com.example.pokedexmultip.android.ui.PokedexScreenState
import com.example.pokedexmultip.android.ui.PokedexViewModel
import com.example.pokedexmultip.network.ImageBuilder
import com.example.pokedexmultiplatform.serializable.PokedexResponse
import com.google.android.material.progressindicator.CircularProgressIndicator

@Composable
fun PokedexScreen(pokedexViewModel: PokedexViewModel) {

    val pokedexList by pokedexViewModel.pokedex.observeAsState(PokedexScreenState.Loading)

    Image(
        painter = painterResource(id = R.drawable.pokedex_background),
        contentDescription = "Fondo de imagen",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillWidth
    )

    when (pokedexList) {
        PokedexScreenState.Error -> {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding()
                    .padding(2.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AsyncImage(
                    model = R.drawable.pikachu_llorando,
                    placeholder = painterResource(id = R.drawable.pokeball),
                    contentDescription = "Imagen de pikachu",
                    modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.small),
                    contentScale = ContentScale.FillWidth
                )

                Text(
                    text = "HA OCURRIDO UN ERROR",
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }

        }

        PokedexScreenState.Loading -> {

            Box(
                modifier = Modifier
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(LocalContext.current)
            }

        }

        is PokedexScreenState.ShowPokedex -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 2.dp, horizontal = 0.5.dp)
            ) {


                PokedexGrid((pokedexList as PokedexScreenState.ShowPokedex).pokedex)
            }
        }
    }


}

@Composable
private fun PokedexGrid(pokedexList: List<PokedexResponse>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        contentPadding = PaddingValues(3.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 2.dp, horizontal = 0.5.dp),
        verticalArrangement = Arrangement.Top,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        items(pokedexList) {
            PokemonCard(pokemon = it)
        }
    }
}


@Composable
private fun PokemonCard(pokemon: PokedexResponse) {

    Card(
        modifier = Modifier
            .size(width = 0.dp, height = 135.dp)
            .safeContentPadding()
            .padding(3.dp),
        shape = MaterialTheme.shapes.medium
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(2.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = ImageBuilder.buildPokemonImageByUrl(pokemon.url),
                placeholder = painterResource(id = R.drawable.pokeball),
                contentDescription = "Imagen de ${pokemon.name}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = MaterialTheme.shapes.small)
            )

            Text(
                text = pokemon.name.uppercase(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        }

    }

}