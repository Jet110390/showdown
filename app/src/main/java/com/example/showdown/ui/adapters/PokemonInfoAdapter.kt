package com.example.showdown.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.showdown.data.local.entities.Pokemon
import com.example.showdown.databinding.PokeItemBinding
import com.example.showdown.ui.view.MainFragmentDirections
import com.example.showdown.ui.viewmodel.PokemonInfoViewModel
import kotlinx.coroutines.flow.flowOf

import kotlinx.coroutines.runBlocking

class PokemonInfoAdapter(
    private val pokemonInfoViewModel: PokemonInfoViewModel
        ) : RecyclerView.Adapter<PokemonInfoAdapter.PokemonViewHolder>(),Filterable {
    private var pokemonList = mutableListOf<Pokemon?>()
    var pokemonListCopy = mutableListOf<Pokemon?>()
    var pokemonListFiltered = mutableListOf<Pokemon?>()
    private var _officialArtworkUrl: MutableLiveData<String> = MutableLiveData()
    val officialArtworkUrl: LiveData<String> get() = _officialArtworkUrl
//    private lateinit var pokemonInfoViewModel: PokemonInfoViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding).listen { pos, type ->
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.addFavorite(pokemonList[position]!!,pokemonInfoViewModel)
        holder.bind(pokemonList[position])
        holder.itemView.setOnClickListener {
            _officialArtworkUrl.postValue(holder.getOfficialImage(pokemonList[position]!!, pokemonInfoViewModel).value)
            Log.d("clicked","name: ${pokemonList[position]?.name} url: ${officialArtworkUrl.value}")


//            Log.d("should show", "${officialArtworkUrl.value}")
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

    fun updateList(pokes: List<Pokemon>) {
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
                pokemonList = if (charString.isNotEmpty()) {
                    pokemonListFiltered
                } else {
                    pokemonListCopy
                }
                notifyDataSetChanged()
            }
        }
    }

    class PokemonViewHolder (private val binding: PokeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        lateinit var viewModel: PokemonInfoViewModel
        fun addFavorite(poke: Pokemon,viewModel: PokemonInfoViewModel){
//            if (binding.addToFavsBtn.isVisible)
            binding.addToFavsBtn.setOnClickListener {
                runBlocking {
                    viewModel.addToFavs(poke) }
            }
        }
        fun getOfficialImage(poke: Pokemon, viewModel: PokemonInfoViewModel): LiveData<String> {
            return viewModel.getOfficialImage(poke.id)
        }


        fun bind(pokemon: Pokemon?) = with(binding) {

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
