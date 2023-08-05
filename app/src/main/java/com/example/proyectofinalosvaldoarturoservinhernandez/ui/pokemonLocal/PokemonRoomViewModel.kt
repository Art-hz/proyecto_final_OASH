package com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonLocal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.database.PokemonDatabase
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.database.PokemonRepo
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.database.model.PokemonRoomModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class PokemonRoomViewModel(application: Application): AndroidViewModel(application) {

    val readAllPoke: LiveData<List<PokemonRoomModel>>
    private val repo: PokemonRepo

    init {
        val pokemonDao = PokemonDatabase.getDatabase(application.applicationContext).dao()
        repo = PokemonRepo(pokemonDao)
        readAllPoke = repo.readAllData
    }

    fun addPokemon(pokemon: PokemonRoomModel) {
        viewModelScope.launch(Dispatchers.IO) {
             repo.addPokemon(pokemon)
        }
    }
}