package com.example.proyectofinalosvaldoarturoservinhernandez.ui.pokemonList.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.database.dao.PokemonDao
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.PokemonRepository
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.model.ApiResponseStatus
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.model.response.PokemonListResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(): ViewModel() {


    var pokemonList = mutableStateOf<List<PokemonListResponse.PokemonModel>>(listOf())
        private set

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set
    private val pokemonRepository = PokemonRepository()




    fun resetApiResponseStatus() {
        status.value = null
    }

    init {
    getPokemonCollection()
    }

    fun getPokemonCollection() {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(pokemonRepository.getPokemonCollection())
        }
    }


    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<PokemonListResponse.PokemonModel>>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            pokemonList.value = apiResponseStatus.data!!
        }

        status.value = apiResponseStatus as ApiResponseStatus<Any>
    }
}