package com.example.pokedex.ui.fragments.pokemonList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.data.PokemonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val repository: PokemonRepository): ViewModel() {

    var pokemonListLiveData = MutableLiveData<Response<PokemonResponse>>()

    fun getPokemonList() = viewModelScope.launch {
        pokemonListLiveData.value = repository.getPokemonList()
    }
}