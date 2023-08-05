package com.example.proyectofinalosvaldoarturoservinhernandez.ui.database

import androidx.lifecycle.LiveData
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.database.dao.PokemonDao
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.database.model.PokemonRoomModel

class PokemonRepo(private val pokemonDao: PokemonDao) {
    val readAllData: LiveData<List<PokemonRoomModel>> = pokemonDao.getAllPokemonsStored()

    suspend fun addPokemon(poke: PokemonRoomModel){
        pokemonDao.addPokemon(poke)
    }
}