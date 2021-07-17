package com.example.pokedex.ui.fragments.pokemonList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.databinding.FragmentPokemonListBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class PokemonListFragment : Fragment() {
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
                return@Observer
            } catch (e: HttpException) {
                Log.e(TAG, "Http Exception Occurred")
                return@Observer
            }
            if (response.isSuccessful) {
                adapter.submitList(response.body()?.results)
            }
        })

        return view
    }

    private fun setUPRecyclerView() {
        adapter = PokemonListAdapter()
        binding.apply {
            pokemonListRecyclerView.adapter = adapter
            pokemonListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}