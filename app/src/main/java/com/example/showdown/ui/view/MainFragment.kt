package com.example.showdown.ui.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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

@AndroidEntryPoint
class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private val infoViewModel: PokemonInfoViewModel by viewModels()

    private lateinit var dexList: List<Pokemon>

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
        startShimmer()
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
            gen1.setOnClickListener {
                infoViewModel.getGenOne().value?.let { firstGen ->
                    pokemonAdapter.updateList(firstGen) }
            }
            gen2.setOnClickListener {
                infoViewModel.getGenTwo().value?.let { secondGen ->
                    pokemonAdapter.updateList(secondGen) }
            }
            gen3.setOnClickListener {
                infoViewModel.getGenThree().value?.let { thirdGen ->
                    pokemonAdapter.updateList(thirdGen) }
            }
            gen4.setOnClickListener {
                infoViewModel.getGenFour().value?.let { fourthGen ->
                    pokemonAdapter.updateList(fourthGen) }
            }
            gen5.setOnClickListener {
                infoViewModel.getGenFive().value?.let { fifthGen ->
                    pokemonAdapter.updateList(fifthGen) }
            }
            gen6.setOnClickListener {
                infoViewModel.getGenSix().value?.let { sixthGen ->
                    pokemonAdapter.updateList(sixthGen) }
            }
            gen7.setOnClickListener {
                infoViewModel.getGenSeven().value?.let { seventhGen ->
                    pokemonAdapter.updateList(seventhGen) }
            }
            gen8.setOnClickListener {
                infoViewModel.getGenEight().value?.let { eighthGen ->
                    pokemonAdapter.updateList(eighthGen) }
            }
            gen9.setOnClickListener {
                infoViewModel.getGenNine().value?.let { ninthGen ->
                    pokemonAdapter.updateList(ninthGen) }
            }
            allGens.setOnClickListener {
                infoViewModel.allPokes().value?.let { allGens ->
                    pokemonAdapter.updateList(allGens) }
            }

//            Glide.with(selectedPokeImageIv.context)
//                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/254.png")
//                .load("${pokemonAdapter.officialArtworkUrl.value}")
//                .placeholder(R.drawable.ic_launcher_foreground)
//                .signature(ObjectKey(System.currentTimeMillis().toString()))
//                .listener(object : RequestListener<Drawable?> {
//                    override fun onLoadFailed(
//                        @Nullable e: GlideException?,
//                        model: Any?,
//                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        // log exception
//                        Log.e("TAG", "Error loading image", e)
//                        return false // important to return false so the error placeholder can be placed
//                    }
//
//                    override fun onResourceReady(
//                        resource: Drawable?,
//                        model: Any?,
//                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
//                        dataSource: DataSource?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        return false
//                    }
//                })
//                .into(selectedPokeImageIv)

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
//                Handler(Looper.getMainLooper()).postDelayed({
                    // Stop shimmer effect
                    stopShimmer()
                    pokeRv.apply {
//                    this.adapter?.notifyDataSetChanged()
                        infoViewModel
                        adapter = pokemonAdapter
                        layoutManager =
                            GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)

//                        LinearLayoutManager(requireContext())
                    }
//                }, 3000)
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
    private fun startShimmer() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
    }

    private fun stopShimmer() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
    }

    private fun updateUi(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


