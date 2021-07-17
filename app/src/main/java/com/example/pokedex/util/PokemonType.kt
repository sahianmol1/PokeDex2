package com.example.pokedex.util

enum class PokemonType {
    fighting,
    flying,
    poison,
    ground,
    rock,
    bug,
    ghost,
    steel,
    fire,
    water,
    grass,
    electric,
    psychic,
    ice,
    dragon,
    fairy,
    dark;

    companion object {
        fun getRandom(): PokemonType {
            return values()[(Math.random() * values().size).toInt()]
        }
    }
}