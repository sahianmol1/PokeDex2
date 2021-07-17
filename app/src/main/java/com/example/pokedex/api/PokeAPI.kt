package com.example.pokedex.api

import com.example.pokedex.data.PokemonResponse
import com.example.pokedex.models.PokemonInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeAPI {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") size: Int = 60,
        @Query("offset") offSet: Int = 60
    ): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Response<PokemonInfo>
}