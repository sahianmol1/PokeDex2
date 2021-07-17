package com.example.pokedex.data

import com.example.pokedex.api.PokeAPI
import javax.inject.Inject

class PokemonRepository @Inject constructor(val pokeAPI: PokeAPI) {

    suspend fun getPokemonList() = pokeAPI.getPokemonList()

}