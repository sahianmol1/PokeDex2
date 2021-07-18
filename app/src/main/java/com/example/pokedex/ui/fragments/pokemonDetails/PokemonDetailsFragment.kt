package com.example.pokedex.ui.fragments.pokemonDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonDetailsBinding
import com.example.pokedex.util.PokemonTypeUtils
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailsBinding
    private val viewModel: PokemonDetailViewModel by viewModels()
    private val args: PokemonDetailsFragmentArgs by navArgs()
    private val TAG = "PokemonDetails Fragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)

        Glide.with(binding.root)
            .load(args.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerInside()
            .into(binding.imgPokemon)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = ""

        viewModel.getPokemonInfo(args.pokemonName)
        viewModel.pokemonDetailLiveData.observe(viewLifecycleOwner, Observer {
            val response = try {
                it
            } catch (e: IOException) {
                Log.e(TAG, "IO Exception Occurred")
                binding.loadingIndicator.visibility = View.GONE
                return@Observer
            } catch (e: HttpException) {
                Log.e(TAG, "Http Exception Occurred")
                binding.loadingIndicator.visibility = View.GONE
                return@Observer
            }
            if (response.isSuccessful) {
                binding.loadingIndicator.visibility = View.GONE
                binding.apply {
                    tvPokemonName.text = response.body()?.name
                    pokemonType.text = response.body()?.types?.get(0)?.type?.name ?: "Water"
                    pokemonHeightValue.text = getString(R.string.pokemon_height_value,
                        (response.body()?.height)?.div(10f).toString().trim()
                    )
                    pokemonWeightValue.text = getString(R.string.pokemon_weight_value,
                        (response.body()?.weight)?.div(10f).toString().trim()
                    )

                    val tint = ContextCompat.getColor(requireContext(), PokemonTypeUtils.getTypeColor(response.body()?.types?.get(0)?.type?.name!!))
                    pokemonType.background.setTint(tint)
                    toolbar.setBackgroundColor(tint)
                    collapsingToolbar.setBackgroundColor(tint)
                    fatherConstraintLayout.setBackgroundColor(tint)
                }
            }

        })
        return binding.root
    }


}