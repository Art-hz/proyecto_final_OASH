package com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonList.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.database.model.PokemonRoomModel
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.model.response.PokemonListResponse
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonList.viewmodel.PokemonListViewModel
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonLocal.LocalPokemonActivity
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonLocal.PokemonRoomViewModel
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.theme.ProyectoFinalOsvaldoArturoServinHernandezTheme

class MainActivity : ComponentActivity() {
    private val viewModel : PokemonListViewModel by viewModels()
    private var roomViewModel : PokemonRoomViewModel? = null

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomViewModel = ViewModelProvider(this).get(PokemonRoomViewModel::class.java)
        Toast.makeText(this@MainActivity, "Da tap sobre un pokemon para agregarlo a favoritos", Toast.LENGTH_LONG).show()
        setContent {
            ProyectoFinalOsvaldoArturoServinHernandezTheme {
                // A surface container using the 'background' color from the theme

                PokemonListScreen(
                    onNavigationIconClick = {
                                      viewModel.getPokemonCollection()
                    },
                    onPokemonClicked = { addToFavorites(it) },
                    viewModel = viewModel
                )
            }
        }
    }

    private fun addToFavorites(pokemon: PokemonListResponse.PokemonModel){
        val converted = PokemonRoomModel(id = 0, pokemon.name, pokemon.url)
        roomViewModel?.addPokemon(converted)

        startActivity(Intent(this@MainActivity, LocalPokemonActivity::class.java))
    }
}


