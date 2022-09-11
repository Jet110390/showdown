package com.example.showdown.ui.view

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.showdown.data.local.entities.Pokemon
import com.example.showdown.databinding.FragmentMainBinding
import com.example.showdown.ui.adapters.PokemonInfoAdapter
import com.example.showdown.ui.viewmodel.PokemonInfoViewModel
import com.example.showdown.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private val infoViewModel: PokemonInfoViewModel by viewModels()

    private val pokemonAdapter by lazy {
        PokemonInfoAdapter(infoViewModel)
//        PokemonInfoAdapter(::addFav)
    }
    fun onItemClick(position: Int){

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
//    private fun addFav(fav: Pokemon) {
//        runBlocking {  infoViewModel.addToFavs(fav)}
//    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

//            pokemonAdapter.setOnItemClickListener(object : PokemonInfoAdapter.onItemClickListener{
//                override fun onItemClick(position: Int) {
//                    val direction = MainFragmentDirections.mainFragmentToPokedexFragmentAction()
//                    findNavController().navigate(direction)
//                }
//
//            })
            favsBtn.setOnClickListener {
                val direction = MainFragmentDirections.mainFragmentToFavsFragmentAction()
                findNavController().navigate(direction)
            }
            teamBtn.setOnClickListener {
                val direction = MainFragmentDirections.mainFragmentToTeamsFragmentAction()
                findNavController().navigate(direction)
            }
            WTPBtn.setOnClickListener {
                val direction = MainFragmentDirections.mainFragmentToGameFragmentAction()
                findNavController().navigate(direction)
            }
            pokeSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
//                    pokeSv.clearFocus()
                    Log.d("submit", "$query")
                    pokemonAdapter.filter.filter(query)
                  return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d("query", "$newText")
                    pokemonAdapter.filter.filter(newText)
                  return false
                }

            })
//            pokeSv.setOnSuggestionListener(object: SearchView.OnSuggestionListener,
//                android.widget.SearchView.OnSuggestionListener{
//                override fun onSuggestionSelect(position: Int): Boolean {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onSuggestionClick(position: Int): Boolean {
//                    TODO("Not yet implemented")
//                }
//            })



            infoViewModel.fullPokedex.observe(viewLifecycleOwner) {
                //add animations to recycler view
                pokeRv.apply {
//                    this.adapter?.notifyDataSetChanged()
                    infoViewModel
                    adapter = pokemonAdapter
                    layoutManager =
                        GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
//                        LinearLayoutManager(requireContext())
                }
            }
            infoViewModel.fullPokedex.observe(viewLifecycleOwner) { state ->
                updateUi(state is DataState.Loading)
                when (state) {
                    is DataState.Loading -> {
                        // no-op
                    }
                    is DataState.Success -> {
                        pokemonAdapter.submitList(state.data)
                    }
                    is DataState.Error -> {
                        Toast.makeText(requireContext(), state.msg, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


    private fun updateUi(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


