package com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonListResponse (
    @Expose
    @SerializedName("count")
    val count: Int?,
    @Expose
    @SerializedName("next")
    val next: String?,
    @Expose
    @SerializedName("previous")
    val previous: String?,
    @Expose
    @SerializedName("results")
    val pokemons: ArrayList<PokemonModel>
        ) {
    data class PokemonModel(
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("url")
        val url: String
    )
}