package com.example.showdown.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.showdown.data.local.entities.Variant
import com.example.showdown.databinding.FavsItemBinding
import com.example.showdown.databinding.VariantItemBinding
import com.example.showdown.ui.view.MainFragmentDirections
import com.example.showdown.ui.view.PokedexFragmentDirections
import com.example.showdown.ui.viewmodel.VariantViewModel
import kotlinx.coroutines.runBlocking


class VariantAdapter(
    private val variantsList: List<Variant>,
    private val variantViewModel: VariantViewModel
) : RecyclerView.Adapter<VariantAdapter.VariantViewHolder>(){
//    private val variantViewModel: VariantViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantAdapter.VariantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VariantItemBinding.inflate(inflater, parent, false)
        return VariantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VariantViewHolder, position: Int) {
//        val currentPoke=pokemonList[position]
        holder.addFavorite(variantsList[position]!!,variantViewModel)
        holder.itemView.setOnClickListener {
            Log.d("clicked","${variantsList[position]?.name}")
//            val variantsList = pokemonInfoViewModel.getVars(pokemonList[position]!!)
            val directions = PokedexFragmentDirections.pokedexFragmentToVariantFragmentAction(variantsList[position]!!)
            holder.itemView.findNavController().navigate(directions)
        }
        holder.bind(variantsList[position])
//        holder.itemView.setOnClickListener {
//            Log.d("clicked","${favsList[position]?.name}")
//            val variantsList = pokemonInfoViewModel.getVars(pokemonList[position]!!)
//            val directions = MainFragmentDirections.mainFragmentToPokedexFragmentAction(pokemonList[position]!!)
//            holder.itemView.findNavController().navigate(directions)
//            Navigation.createNavigateOnClickListener(directions)

//                MainFragmentDirections.mainFragmentToTeamsFragmentAction()
//                findNavController().navigate(direction)
//            }
//        }
//        View.OnClickListener {
//            Log.d("clicked","${pokemonList[position]}")
//            runBlocking { pokemonList[position]?.let {
//                pokemonInfoViewModel.addToFavs(it) }
//            }
//        }


//        if (currentPoke != null) {
//            runBlocking {
//            holder.addFav(currentPoke, pokemonInfoViewModel)}
//        }
    }

    override fun getItemCount(): Int {
        return variantsList.size
    }

    class VariantViewHolder(private val binding: VariantItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

                fun addFavorite(variant: Variant, viewModel: VariantViewModel){
                    binding.variantfavBtn.setOnClickListener {
                        runBlocking {
                            viewModel.addToFavs(variant)
                        }
                    }
                }

                fun bind(variant: Variant){
                    with(binding){
                        variantInfoTv.text = "${variant.name}\n${if (variant.type2 != "none") {
                            "${variant.type1}/${variant.type2}"
                        } else {
                            "${variant.type1}"
                        }}"
                        Glide.with(variantPokeIv.context).load("${variant?.image}").into(variantPokeIv)
//                        variantfavBtn.setOnClickListener {
//
//                        }
                    }
                }
            }
}