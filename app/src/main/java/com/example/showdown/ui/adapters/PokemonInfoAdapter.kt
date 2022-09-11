package com.example.showdown.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.data.local.entities.Pokemon
import com.example.showdown.data.local.entities.Variant
import com.example.showdown.databinding.PokeItemBinding
import com.example.showdown.ui.view.MainFragmentDirections
import com.example.showdown.ui.viewmodel.PokemonInfoViewModel

import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.reflect.KFunction1

class PokemonInfoAdapter(
    private val pokemonInfoViewModel: PokemonInfoViewModel
        ) : RecyclerView.Adapter<PokemonInfoAdapter.PokemonViewHolder>(),Filterable {
    private var pokemonList = mutableListOf<Pokemon?>()
    var pokemonListCopy = mutableListOf<Pokemon?>()
    var pokemonListFiltered = mutableListOf<Pokemon?>()
//    private lateinit var pokemonInfoViewModel: PokemonInfoViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PokeItemBinding.inflate(inflater, parent, false)
        return PokemonViewHolder(binding).listen { pos, type ->
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.addFavorite(pokemonList[position]!!,pokemonInfoViewModel)
        holder.bind(pokemonList[position])
        holder.itemView.setOnClickListener {
            Log.d("clicked","${pokemonList[position]?.name}")
//            val variantsList = pokemonInfoViewModel.getVars(pokemonList[position]!!)
            val directions = MainFragmentDirections.mainFragmentToPokedexFragmentAction(pokemonList[position]!!)
            holder.itemView.findNavController().navigate(directions)
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun submitList(pokes: List<Pokemon>) {
        pokemonList.clear()
        pokemonList.addAll(pokes)
        pokemonListCopy = pokemonList
//        pokemonListFiltered
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) {
                    pokemonListFiltered = pokemonList
                } else {
                    val filteredList = mutableListOf<Pokemon?>()
                    pokemonList
                        .filter {
                            (it?.name?.contains(constraint!!) == true) or (it?.type1?.contains(
                                constraint!!
                            ) == true) or (it?.type2?.contains(constraint!!) == true) or (it?.id?.toString() == constraint!!)
//                            (it?.name?.contains(constraint!!))?.or((it?.type1?.contains(constraint!!)) ?:)

                        }
                        .forEach { filteredList.add(it) }
//                    Log.d("filtered", "$filteredList")
                    pokemonListFiltered = filteredList

                }
                return FilterResults().apply { values = pokemonListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                Log.d("results","${results?.values}")
                val charString = constraint?.toString() ?: ""
                pokemonListFiltered = if (results?.values == null) {
                    ArrayList()
                } else {
//                    Log.d("published","${results.values}")
                    results.values as MutableList<Pokemon?>

                }
                if (charString?.length > 0) {
                    pokemonList = pokemonListFiltered
                } else {
                    pokemonList = pokemonListCopy
                }
                notifyDataSetChanged()
            }
        }
    }

    class PokemonViewHolder (private val binding: PokeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun addFavorite(poke: Pokemon,viewModel: PokemonInfoViewModel){
//            if (binding.addToFavsBtn.isVisible)
            binding.addToFavsBtn.setOnClickListener {
                runBlocking {
                    viewModel.addToFavs(poke) }
            }
        }
        fun bind(pokemon: Pokemon?) {

            with(binding) {
                if (pokemon != null) {

                    idTv.text = "#${pokemon.id}"
                    nameTv.text = " ${pokemon.name.upper()}"
                    typeTv.text = "${
                        if (pokemon.type2 != "none") {
                            "${pokemon.type1.upper()}/${pokemon.type2.upper()}"
                        } else {
                            "${pokemon.type1.upper()}"
                        }
                    }"

                    }
                    Glide.with(pokeIv.context).load("${pokemon?.image}").into(pokeIv)
            }
        }
    }
}
//extension funct
private fun String?.upper(): String {
    var rest=this?.substring(1,this.length)
    var first=this?.substring(0,1)
    return first?.uppercase()+rest
}


fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(getAdapterPosition(), getItemViewType())
    }
    return this
}
