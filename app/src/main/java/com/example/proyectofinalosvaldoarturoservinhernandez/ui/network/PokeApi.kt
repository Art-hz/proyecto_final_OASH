package com.example.proyectofinalosvaldoarturoservinhernandez.ui.network

import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.model.response.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,

    ): PokemonListResponse
}