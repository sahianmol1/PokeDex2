package com.example.pokedex.ui.fragments.pokemonList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.data.PokemonResponse
import com.example.pokedex.models.PokemonInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {

    var pokemonListLiveData = MutableLiveData<Response<PokemonResponse>>()
    var pokemonInfoLiveData = MutableLiveData<Response<PokemonInfo>>()
    lateinit var pokemonType: String

    fun getPokemonList() = viewModelScope.launch {
        pokemonListLiveData.value = repository.getPokemonList()
    }

    fun getPokemonInfo(name: String) = viewModelScope.launch {
        pokemonInfoLiveData.value = repository.getPokemonInfo(name)
    }

    fun getPokemonType(name: String) = viewModelScope.launch {
        val response = repository.getPokemonInfo(name)
        if (response.isSuccessful) {
            pokemonType = response.body()?.types?.get(0)?.type?.name.toString()
        }
    }
}