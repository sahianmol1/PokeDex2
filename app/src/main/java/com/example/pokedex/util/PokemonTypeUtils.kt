package com.example.pokedex.util

import com.example.pokedex.R
import com.example.pokedex.util.PokemonType.Companion.getRandom

object PokemonTypeUtils {
    fun getTypeColor(): Int {
        return when (getRandom()) {
            PokemonType.fighting -> R.color.fighting
            PokemonType.flying -> R.color.flying
            PokemonType.poison -> R.color.poison
            PokemonType.ground -> R.color.ground
            PokemonType.rock -> R.color.rock
            PokemonType.bug -> R.color.bug
            PokemonType.ghost -> R.color.ghost
            PokemonType.steel -> R.color.steel
            PokemonType.fire -> R.color.fire
            PokemonType.water -> R.color.water
            PokemonType.grass -> R.color.grass
            PokemonType.electric -> R.color.electric
            PokemonType.psychic -> R.color.psychic
            PokemonType.ice -> R.color.ice
            PokemonType.dragon -> R.color.dragon
            PokemonType.fairy -> R.color.fairy
            PokemonType.dark -> R.color.dark
            else -> R.color.gray_21
        }
    }
}