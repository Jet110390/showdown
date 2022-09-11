package com.example.showdown.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.showdown.databinding.FragmentVariantBinding
import com.example.showdown.ui.viewmodel.VariantViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class VariantFragment: Fragment() {
    private var _binding: FragmentVariantBinding? = null
    private val binding: FragmentVariantBinding get() = _binding!!

    private val variantViewModel: VariantViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVariantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle= arguments
        if(bundle==null){
            Log.d("bundle error","variant fragment didn't receive info")
            return
        }
        val args = VariantFragmentArgs.fromBundle(bundle)

        with(binding){
            val variant = args.variantPokemon
            variantHeaderTv.text="${variant?.name}\n ${ if (variant?.type2 != "none") {
                            "${variant?.type1}/${variant?.type2} type"
                        } else {
                            "${variant?.type1} type"
                        }
                    }"
            Glide.with(variantIv.context).load("${variant?.officialImg}").into(variantIv)
            variantAddToFavsBtn.setOnClickListener {
                runBlocking { variantViewModel.addToFavs(variant!!) }

            }
            variantStatsTv.text="Height: ${variant?.height?.toFloat()?.div(10.0)}m\n Weight: ${variant?.weight?.toFloat()?.div(10.0)}kg"
        }
    }
}