package com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonLocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinalosvaldoarturoservinhernandez.R
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.database.model.PokemonRoomModel
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.model.response.PokemonListResponse
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonList.view.DogListScreenTopBar
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonList.view.PokemonGridItem
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonLocal.ui.theme.ProyectoFinalOsvaldoArturoServinHernandezTheme
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.utils.loadImage

class LocalPokemonActivity : ComponentActivity() {

    private var roomViewModel : PokemonRoomViewModel? = null
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        roomViewModel = ViewModelProvider(this).get(com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonLocal.PokemonRoomViewModel::class.java)
        super.onCreate(savedInstanceState)

        roomViewModel?.readAllPoke?.observe(this) { localList ->
            setContent {
                ProyectoFinalOsvaldoArturoServinHernandezTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Scaffold(
                            topBar = { DogListScreenTopBar({finish()}) }
                        ) {
                            Text(text = "Pokemones favoritos")
                            LazyVerticalGrid(
                                contentPadding = it,
                                columns = GridCells.Fixed(2),
                                content = {
                                    items(localList) { poke ->
                                        PokemonGridItemLocal(poke = poke)
                                    }
                                },
                            )
                        }
                    }
                }
            }
        }

    }
}

@ExperimentalMaterialApi
@Composable
fun PokemonGridItemLocal(poke: PokemonRoomModel) {

    androidx.compose.material.Surface(
        modifier = Modifier
            .padding(8.dp)
            .height(100.dp)
            .width(100.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        val number = if (poke.imageUrl.endsWith("/")) {
            poke.imageUrl.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            poke.imageUrl.takeLastWhile { it.isDigit() }
        }
        val finalUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
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


        androidx.compose.material.Text(
            textAlign = TextAlign.Center,
            text = poke.name,
        )
    }

}
