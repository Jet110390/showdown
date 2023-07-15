package com.example.showdown.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.showdown.R
import com.example.showdown.databinding.FragmentFavsBinding
import com.example.showdown.ui.adapters.FavPokemonAdapter
import com.example.showdown.ui.viewmodel.FavPokemonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavsFragment : Fragment() {
    /**
     * add functionality so that when a user clicks
     * they are redirected to
     * idk if this will work due to variants
     */


    private var _binding: FragmentFavsBinding? = null
    private val binding: FragmentFavsBinding get() = _binding!!

    val favsViewModel: FavPokemonViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            favsBackBtn.setOnClickListener {
                val direction = FavsFragmentDirections.favsFragmentToMainFragmentAction()
                findNavController().navigate(direction)
            }
            favsHeaderTv.text = resources.getString(
                R.string.favoritePokemonAmount,
                favsViewModel.favPokes.value?.size.toString()
            )
            Log.d("favs", "${favsViewModel.favPokes.value?.size} Favorite Pokemon")
            favsViewModel.favPokes.observe(viewLifecycleOwner) { favPokes ->
                //add animations to recycler view
                favsRv.apply {
//                    this.adapter?.notifyDataSetChanged()
//                    favsViewModel
                    adapter = favPokes?.let { FavPokemonAdapter(it, favsViewModel) }
                    layoutManager =
                        GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
//                        LinearLayoutManager(requireContext())


                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}