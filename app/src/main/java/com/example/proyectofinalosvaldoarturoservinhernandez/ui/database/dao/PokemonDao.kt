package com.example.proyectofinalosvaldoarturoservinhernandez.ui.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.database.model.PokemonRoomModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Upsert
    suspend fun addPokemon(pokemon: PokemonRoomModel)

    @Delete
    suspend fun deletePokemon(pokemon: PokemonRoomModel)

    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemonsStored(): LiveData<List<PokemonRoomModel>>
}