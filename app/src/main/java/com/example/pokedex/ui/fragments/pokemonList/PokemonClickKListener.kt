package com.example.pokedex.ui.fragments.pokemonList

import android.widget.ImageView
import android.widget.TextView
import com.example.pokedex.data.Result

interface PokemonClickKListener {
    fun onPokemonCardClick(pokemon: Result, image: String, imageView: ImageView, textView: TextView)
}