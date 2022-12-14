package com.example.showdown.ui.view

//import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
//import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.showdown.databinding.FragmentPokedexBinding
import com.example.showdown.ui.adapters.PokemonInfoAdapter
import com.example.showdown.ui.adapters.VariantAdapter
import com.example.showdown.ui.viewmodel.FavPokemonViewModel
import com.example.showdown.ui.viewmodel.PokemonInfoViewModel
import com.example.showdown.ui.viewmodel.VariantViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class PokedexFragment: Fragment() {
    private var _binding: FragmentPokedexBinding? = null
    private val binding: FragmentPokedexBinding get() = _binding!!

    private val favPokeViewModel: FavPokemonViewModel by viewModels()
    private val variantViewModel: VariantViewModel by viewModels()

//    private val variantAdapter by lazy {
//        VariantAdapter(variantViewModel)
////        PokemonInfoAdapter(::addFav)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root
    }

//val args
//    val args
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle= arguments
    if(bundle==null){
        Log.d("bundle error","poxedex fragment didn't receive info")
        return
    }
    Log.d("bundle","${bundle}")
    val args = PokedexFragmentArgs.fromBundle(bundle)
//    Log.d("variants","${ dexInfoViewModel.getVariantInfo(args.pokemonDexEntry!! )}")
//    val variants= dexInfoViewModel.getVars(args.pokemonDexEntry!!)
//    Log.d("variants","$variants")
//    bundle.
//    val args=PokedexFragmentArgs

        with(binding) {
            val pokemon=args.pokemonDexEntry
            if (pokemon != null) {
                variantViewModel.getVariantInfo(pokemon)
                Log.d("variants","${variantViewModel.getVariantInfo(pokemon)}")
            }

            variantViewModel.variantData.observe(viewLifecycleOwner) {variantData->
                //add animations to recycler view
                variantRv.apply {
//                    this.adapter?.notifyDataSetChanged()
//                    variantViewModel.getVariantInfo(pokemon!!)
                    adapter = variantData?.let{VariantAdapter(it,variantViewModel)}
                    layoutManager =
//                        GridLayoutManager(requireContext(), 3, RecyclerView.HORIZONTAL, false)
                        LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL, false)
                }
            }

            pokedexHeaderTv.text="#${pokemon?.id} ${pokemon?.name.upper()}, the ${pokemon?.title}\n${pokemon?.name.upper()} is a ${
                if (pokemon?.type2 != "none") {
                    "${pokemon?.type1.upper()}/${pokemon?.type2.upper()} type"
                } else {
                    "${pokemon?.type1.upper()} type"
                }
            } pokemon${(if(pokemon?.preEvo != "no pre-evolution" ){",\n it evolves from ${pokemon?.preEvo.upper()}"}else{""})}"

            pokedexStatsTv.text="Height: ${pokemon?.height?.toFloat()?.div(10.0)}m\nWeight: ${pokemon?.weight?.toFloat()?.div(10.0)}kg\nhp: ${pokemon?.hp}\natk: ${pokemon?.atk}\ndef: ${pokemon?.def}\nspAtk: ${pokemon?.spAtk}\nspDef: ${pokemon?.spDef}\nspd: ${pokemon?.spd}"

            pokedexDescriptionTv.text="${pokemon?.description}"
            Glide.with(pokedexIv.context).load("${pokemon?.officialImg}").into(pokedexIv)
           likeBtn.setOnClickListener {
                runBlocking { favPokeViewModel.addToFavs(pokemon!!) }

                }

//                val direction = PokedexFragmentDirections.pokedexFragmentToMainFragmentAction()
//                findNavController().navigate(direction)
//            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun String?.upper(): String {
    var rest=this?.substring(1,this.length)
    var first=this?.substring(0,1)
    return first?.uppercase()+rest
}