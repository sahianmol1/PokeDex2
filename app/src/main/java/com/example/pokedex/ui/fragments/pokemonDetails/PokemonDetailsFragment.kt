package com.example.pokedex.ui.fragments.pokemonDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailsBinding
    val viewModel: PokemonDetailViewModel by viewModels()
    val args: PokemonDetailsFragmentArgs by navArgs()
    val TAG = "PokemonDetails Fragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)

        viewModel.getPokemonInfo(args.pokemonName)
        viewModel.pokemonDetailLiveData.observe(viewLifecycleOwner, Observer {
            val response = try {
                it
            } catch (e: IOException) {
                Log.e(TAG, "IO Exception Occurred")
                return@Observer
            } catch (e: HttpException) {
                Log.e(TAG, "Http Exception Occurred")
                return@Observer
            }
            if (response.isSuccessful) {
                binding.apply {
                    tvPokemonName.text = response.body()?.name
                    pokemonType.text = response.body()?.types?.get(0)?.type?.name ?: "Water"
                    pokemonHeightValue.text = getString(R.string.pokemon_height_value,
                        (response.body()?.height)?.div(10f).toString().trim()
                    )
                    pokemonWeightValue.text = getString(R.string.pokemon_weight_value,
                        (response.body()?.weight)?.div(10f).toString().trim()
                    )
                }
            }

        })


        return binding.root
    }
}