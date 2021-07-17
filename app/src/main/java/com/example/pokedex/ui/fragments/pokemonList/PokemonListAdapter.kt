package com.example.pokedex.ui.fragments.pokemonList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.data.Result
import com.example.pokedex.databinding.ListItemPokemonBinding

class PokemonListAdapter: ListAdapter<Result, PokemonListAdapter.PokemonListViewHolder>(DiffCallBAck()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val binding = ListItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class PokemonListViewHolder(private val binding: ListItemPokemonBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.apply {
                tvPokemonName.text = item.name
                Glide.with(root)
                    .load(item.getImageUrl())
                    .into(imgPokemon)
            }
        }
    }

    class DiffCallBAck: DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean {
            return oldItem == newItem
        }

    }
}