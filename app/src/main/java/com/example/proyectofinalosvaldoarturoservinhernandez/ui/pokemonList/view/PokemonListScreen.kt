package com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonList.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.proyectofinalosvaldoarturoservinhernandez.R
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.model.ApiResponseStatus
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.model.response.PokemonListResponse
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonList.viewmodel.PokemonListViewModel
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.utils.loadImage


private const val GRID_SPAN_COUNT = 3


@OptIn(ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@Composable
fun PokemonListScreen(
    onNavigationIconClick: () -> Unit,
    onPokemonClicked: (PokemonListResponse.PokemonModel) -> Unit,
    viewModel: PokemonListViewModel
) {
    val status = viewModel.status.value
    val pokemonList = viewModel.pokemonList.value
    Scaffold(
        topBar = { DogListScreenTopBar(onNavigationIconClick) }
    ) {
        Image(
            modifier = Modifier.fillMaxWidth().height(50.dp).padding(top = 20.dp),
            painter = painterResource(id = R.drawable.logo_poke), contentDescription = "HeaderPoke")
        LazyVerticalGrid(
            contentPadding = it,
            columns = GridCells.Fixed(GRID_SPAN_COUNT),
            content = {
                items(pokemonList) {
                    poke ->
                    PokemonGridItem(poke = poke, onPokemonClicked)
                }
            },
        )
    }

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {

        ErrorDialog(status.messageId) { viewModel.resetApiResponseStatus() }
    }
}


@Composable
fun DogListScreenTopBar(
    onClick: () -> Unit
) {
    TopAppBar(
        title = { Text("Pokedex") },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = { BackNavigationIcon(onClick) }
    )
}

@ExperimentalMaterialApi
@Composable
fun PokemonGridItem(poke: PokemonListResponse.PokemonModel, onDogClicked: (PokemonListResponse.PokemonModel) -> Unit) {

        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            onClick = { onDogClicked(poke) },
            shape = RoundedCornerShape(4.dp)
        ) {
            val number = if(poke.url.endsWith("/")){
                poke.url.dropLast(1).takeLastWhile { it.isDigit() }
            } else {
                poke.url.takeLastWhile { it.isDigit() }
            }
            val finalUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
            val image = loadImage(url = finalUrl, defaultImg = R.drawable.pika_default).value
            image?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .background(Color.White)
                        .semantics { testTag = "dog-${poke.name}" }
                )
            }


            Text(
                textAlign = TextAlign.Center,
                text = poke.name,
            )
        }

}