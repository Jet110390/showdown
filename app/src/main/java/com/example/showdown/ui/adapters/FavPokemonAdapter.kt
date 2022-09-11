package com.example.showdown.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.databinding.FavsItemBinding
import com.example.showdown.ui.view.FavsFragmentDirections
import com.example.showdown.ui.view.MainFragmentDirections
import com.example.showdown.ui.viewmodel.FavPokemonViewModel
import kotlinx.coroutines.runBlocking

class FavPokemonAdapter(
    private var favsList: List<FavoritePokemon>,
    private val favPokemonViewModel: FavPokemonViewModel

) : RecyclerView.Adapter<FavPokemonAdapter.FavPokemonViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavPokemonAdapter.FavPokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavsItemBinding.inflate(inflater, parent, false)
        return FavPokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavPokemonViewHolder, position: Int) {
        holder.bind(favsList[position])
        holder.removeFav(favsList[position],favPokemonViewModel)
        holder.itemView.setOnClickListener {
            val pokemon= runBlocking { favPokemonViewModel.getPokedexEntry(favsList[position]!!) }
            Log.d("clicked","${favsList[position]?.name}")
//            val variantsList = pokemonInfoViewModel.getVars(pokemonList[position]!!)
            val directions = FavsFragmentDirections.favsFragmentToPokedexFragmentAction(pokemon)
            holder.itemView.findNavController().navigate(directions)
        }
    }

    override fun getItemCount(): Int {
        return favsList.size
    }

    class FavPokemonViewHolder (private val binding: FavsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun removeFav(favorite: FavoritePokemon, viewModel: FavPokemonViewModel){
            binding.unfavBtn.setOnClickListener{
               runBlocking {
                   viewModel.removeFromFavs(favorite)
                   viewModel.getFavs()
               }
            }
        }

        fun bind(favPokemon: FavoritePokemon?) {
            with(binding) {

                if (favPokemon != null) {
                    infoTv.text = "${favPokemon.name}\n ${
                            if (favPokemon?.type2 != "none") {
                                "${favPokemon?.type1}/${favPokemon?.type2}" 
                            } else {
                                "${favPokemon?.type1}"}
                    }"
                }
                Glide.with(favPokeIv.context).load("${favPokemon?.image}").into(favPokeIv)
            }
        }
    }
}
