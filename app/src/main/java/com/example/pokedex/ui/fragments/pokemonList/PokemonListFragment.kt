package com.example.pokedex.ui.fragments.pokemonList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.data.Result
import com.example.pokedex.databinding.FragmentPokemonListBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class PokemonListFragment : Fragment(), PokemonClickKListener{
    private lateinit var binding: FragmentPokemonListBinding
    private lateinit var adapter: PokemonListAdapter
    val TAG = "PokemonList Fragment"

    val viewModel: PokemonListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        val view = binding.root

        setUPRecyclerView()
        viewModel.getPokemonList()

        viewModel.pokemonListLiveData.observe(viewLifecycleOwner, Observer {
            val response = try {
                it
            } catch (e: IOException) {
                Log.e(TAG, "IO Exception Occurred")
                stopShimmer()
                return@Observer
            } catch (e: HttpException) {
                Log.e(TAG, "Http Exception Occurred")
                stopShimmer()
                return@Observer
            }
            if (response.isSuccessful) {
                stopShimmer()
                adapter.submitList(response.body()?.results)
            } else {
                stopShimmer()
            }
        })
        return view
    }

    private fun setUPRecyclerView() {
        adapter = PokemonListAdapter(this)
        binding.apply {
            pokemonListRecyclerView.adapter = adapter
            pokemonListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onResume() {
        super.onResume()
        startShimmer()
    }

    override fun onPause() {
        super.onPause()
        stopShimmer()
    }

    private fun startShimmer() {
        binding.myShimmerLayout.startShimmer()
        binding.myShimmerLayout.visibility = View.VISIBLE
    }

    private fun stopShimmer() {
        binding.apply {
            myShimmerLayout.stopShimmer()
            myShimmerLayout.alpha = 0f
            myShimmerLayout.visibility = View.GONE
        }
    }

    override fun onPokemonCardClick(pokemon: Result) {
        findNavController().navigate(PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailsFragment(pokemon.name))
    }
}