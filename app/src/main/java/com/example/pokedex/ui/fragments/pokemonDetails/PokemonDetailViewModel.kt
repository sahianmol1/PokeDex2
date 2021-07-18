package com.example.pokedex.ui.fragments.pokemonDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.models.PokemonInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository: PokemonRepository): ViewModel() {

    var pokemonDetailLiveData = MutableLiveData<Response<PokemonInfo>>()

    fun getPokemonInfo(name: String) = viewModelScope.launch {
        pokemonDetailLiveData.value = repository.getPokemonInfo(name)
    }
}