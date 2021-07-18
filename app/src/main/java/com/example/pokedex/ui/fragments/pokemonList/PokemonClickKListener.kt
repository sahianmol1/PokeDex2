package com.example.pokedex.ui.fragments.pokemonList

import com.example.pokedex.data.Result

interface PokemonClickKListener {
    fun onPokemonCardClick(pokemon: Result, image: String)
}