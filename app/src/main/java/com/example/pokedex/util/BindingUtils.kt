package com.example.pokedex.util

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter

@BindingAdapter("setColorWithType")
fun ConstraintLayout.setBackgroundColor(type: String) {
    setBackgroundColor(PokemonTypeUtils.getTypeColor(type))
}