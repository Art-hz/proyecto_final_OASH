package com.example.proyectofinalosvaldoarturoservinhernandez.ui.network

import android.util.Log
import com.example.proyectofinalosvaldoarturoservinhernandez.R
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.DogsApi.retrofitService
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.model.ApiResponseStatus
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.model.response.PokemonListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PokemonRepository {

    suspend fun getPokemonCollection(): ApiResponseStatus<List<PokemonListResponse.PokemonModel>> {
        return withContext(Dispatchers.IO) {
            val allPokemonListDeferred = async { downloadPokemons() }


            val pokemonListResponse = allPokemonListDeferred.await()
            Log.e("response", pokemonListResponse.toString())
            when (pokemonListResponse) {
                is ApiResponseStatus.Error -> {
                    pokemonListResponse
                }

                is ApiResponseStatus.Success -> {
                    ApiResponseStatus.Success(pokemonListResponse.data)
                }

                else -> {
                    ApiResponseStatus.Error(R.string.unknown_error)
                }
            }
        }
    }


    private suspend fun downloadPokemons(): ApiResponseStatus<List<PokemonListResponse.PokemonModel>> = makeNetworkCall {
        val pokemonListApiResponse = retrofitService.fetchPokemonList(30, 30)
        pokemonListApiResponse.pokemons
    }



}