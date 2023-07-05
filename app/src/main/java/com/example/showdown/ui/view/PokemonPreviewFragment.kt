package com.example.showdown.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.showdown.databinding.FragmentPokemonPreviewBinding


class PokemonPreviewFragment: Fragment() {
        private var _binding: FragmentPokemonPreviewBinding? = null
        private val binding: FragmentPokemonPreviewBinding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding= FragmentPokemonPreviewBinding.inflate(inflater, container, false)
            return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            selectedPokeNameTv.text=""
            Glide.with(selectedPokeIv.context)
                .load("")
                .into(selectedPokeIv)
            dexBtn.setOnClickListener{}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}