package com.example.pokedex.ui.fragments.pokemonList

import android.widget.ImageView
import com.example.pokedex.data.Result

interface PokemonClickKListener {
    fun onPokemonCardClick(pokemon: Result, image: String, imageView: ImageView)
}