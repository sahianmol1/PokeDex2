package com.example.pokedex.ui.fragments.pokemonDetails

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonDetailsBinding
import com.example.pokedex.util.PokemonTypeUtils
import com.skydoves.progressview.textForm
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

        ViewCompat.setTransitionName(binding.imgPokemon, "pokemonImageTransition")
        ViewCompat.setTransitionName(binding.tvPokemonName, "pokemonNameTransition")

        val sharedElementTransition = TransitionInflater.from(requireContext())
            .inflateTransition(
                android.R.transition.move
            )

        sharedElementEnterTransition = sharedElementTransition

        postponeEnterTransition()

        Glide.with(binding.root)
            .load(args.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerInside()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

            })
            .into(binding.imgPokemon)

        binding.tvPokemonName.text = args.pokemonName

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
                    pokemonType.text = response.body()?.types?.get(0)?.type?.name ?: "Normal"
                    pokemonHeightValue.text = getString(R.string.pokemon_height_value,
                        (response.body()?.height)?.div(10f).toString().trim()
                    )
                    pokemonWeightValue.text = getString(R.string.pokemon_weight_value,
                        (response.body()?.weight)?.div(10f).toString().trim()
                    )

                    val tint = ContextCompat.getColor(requireContext(), PokemonTypeUtils.getTypeColor(response.body()?.types?.get(0)?.type?.name!!))
                    pokemonType.background.setTint(tint)
                    collapsingToolbar.background.setTint(tint)
                    toolbar.setBackgroundColor(tint)

                    val form = textForm(requireContext()) {
                        text = "This is a TextForm"
                        textColor = ContextCompat.getColor(requireContext(), R.color.white_87)
                        textSize = 14f
                        textTypeface = Typeface.BOLD
                    }
                    val randomSpeed = (Math.random() * 300).toFloat()
                    if (((randomSpeed / 300) * 100) > 50) {
                        pvSpd.progress = (randomSpeed / 300) * 100
                    } else {
                        pvSpd.progress = 50F
                    }
                    pvSpd.applyTextForm(form)
                    pvSpd.labelText = "${randomSpeed.toInt()} / 300"

                    val randomAttack = (Math.random() * 1000).toFloat()
                    if (((randomAttack / 1000) * 100) > 50) {
                        pvAtk.progress = (randomAttack / 1000) * 100
                    } else {
                        pvAtk.progress = 50F
                    }
                    pvAtk.applyTextForm(form)
                    pvAtk.labelText = "${randomAttack.toInt()} / 1000"

                    val randomDefence = (Math.random() * 1000).toFloat()
                    if (((randomDefence / 1000) * 100) > 50) {
                        pvDef.progress = (randomDefence / 1000) * 100
                    } else {
                        pvDef.progress = 50F
                    }
                    pvDef.applyTextForm(form)
                    pvDef.labelText = "${randomDefence.toInt()} / 1000"

                    val randomExperience = (Math.random() * 1000).toFloat()
                    if (((randomExperience / 1000) * 100) > 50) {
                        pvExp.progress = (randomExperience / 1000) * 100
                    } else {
                        pvExp.progress = 50F
                    }
                    pvExp.applyTextForm(form)
                    pvExp.labelText = "${randomExperience.toInt()} / 1000"

                    val randomHp = (Math.random() * 1000).toFloat()
                    if (((randomHp / 1000) * 100) > 50) {
                        pvHp.progress = (randomHp / 1000) * 100
                    } else {
                        pvHp.progress = 50F
                    }
                    pvHp.applyTextForm(form)
                    pvHp.labelText = "${randomHp.toInt()} / 1000"

                    pvAtk.progressAnimate()
                    pvDef.progressAnimate()
                    pvExp.progressAnimate()
                    pvHp.progressAnimate()
                    pvSpd.progressAnimate()
                }
            }

        })

        sharedElementReturnTransition = sharedElementTransition

        return binding.root
    }


}