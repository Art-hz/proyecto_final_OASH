package com.example.proyectofinalosvaldoarturoservinhernandez.ui.network

import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.model.response.PokemonListResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL = "https://pokeapi.co/api/v2/"

private val okHttpClient = OkHttpClient
    .Builder()
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ApiService {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,

        ): PokemonListResponse


}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}