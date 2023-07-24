package com.example.showdown.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.showdown.R
import com.example.showdown.databinding.FragmentPokedexBinding
import com.example.showdown.ui.adapters.VariantAdapter
import com.example.showdown.ui.viewmodel.FavPokemonViewModel
import com.example.showdown.ui.viewmodel.VariantViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class PokedexFragment: Fragment() {
    private var _binding: FragmentPokedexBinding? = null
    private val binding: FragmentPokedexBinding get() = _binding!!

    private val favPokeViewModel: FavPokemonViewModel by activityViewModels()
    private val variantViewModel: VariantViewModel by activityViewModels()

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
        val bundle = arguments
        if (bundle == null) {
            Log.d("bundle error", "poxedex fragment didn't receive info")
            return
        }
        Log.d("bundle", "${bundle}")
        val args = PokedexFragmentArgs.fromBundle(bundle)

        with(binding) {
            val pokemon = args.pokemonDexEntry
            if (pokemon != null) {
                variantViewModel.getVariantInfo(pokemon)
                Log.d("variants", "${variantViewModel.getVariantInfo(pokemon)}")
            }
            bulbapediaWv.apply {
                // Enable JavaScript if required for the webpage
                settings.javaScriptEnabled = true

                // Enable zooming in web view
                settings.setSupportZoom(false)
                settings.builtInZoomControls = false
                settings.displayZoomControls = false

                // Zoom web view text
                settings.textZoom = 100

                // Set a WebViewClient to handle page navigation
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        // By default, open all URLs in the WebView
                        return false
                    }
                }
            }
            moreInfoTv.setOnClickListener{
                bulbapediaWv.toggleVisibility()
                dexSv.toggleVisibility()
                if (pokemon != null) {
                    loadBulbapediaPage(pokemon.name.upper(), pokemon.speciesName!!, pokemon.variantAmount!!)
                }
            }
            variantViewModel.variantData.observe(viewLifecycleOwner) { variantData ->
                //add animations to recycler view
                variantRv.apply {
//                    this.adapter?.notifyDataSetChanged()
//                    variantViewModel.getVariantInfo(pokemon!!)
                    adapter = variantData?.let { VariantAdapter(it, variantViewModel) }
                    layoutManager =
//                        GridLayoutManager(requireContext(), 3, RecyclerView.HORIZONTAL, false)
                        LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                }
            }

            pokedexHeaderTv.text =
                "#${pokemon?.id} ${pokemon?.name.upper()}, the ${pokemon?.title}\n${pokemon?.name.upper()} is a ${
                    if (pokemon?.type2 != "none") {
                        "${pokemon?.type1.upper()}/${pokemon?.type2.upper()} type"
                    } else {
                        "${pokemon?.type1.upper()} type"
                    }
                } pokemon${
                    (if (pokemon?.preEvo != "no pre-evolution") {
                        ",\n it evolves from ${pokemon?.preEvo.upper()}"
                    } else {
                        ""
                    })
                }"

            pokedexStatsTv.text = "Height: ${
                pokemon?.height?.toFloat()?.div(10.0)
            }m\nWeight: ${
                pokemon?.weight?.toFloat()?.div(10.0)
            }kg\nhp: ${pokemon?.hp}\natk: ${pokemon?.atk}\ndef: ${pokemon?.def}\nspAtk: ${pokemon?.spAtk}\nspDef: ${pokemon?.spDef}\nspd: ${pokemon?.spd}"

            pokedexDescriptionTv.text = "${pokemon?.description}"
            Glide.with(pokedexIv.context).load("${pokemon?.officialImg}").into(pokedexIv)
            likeBtn.setOnClickListener {
                runBlocking { favPokeViewModel.addToFavs(pokemon!!) }

            }

//                val direction = PokedexFragmentDirections.pokedexFragmentToMainFragmentAction()
//                findNavController().navigate(direction)
//            }
        }
    }
    inner class WrongNames{
        val wrongNamesList = listOf(
        "Nidoran-f",
        "Nidoran-m",
        "Mr-mime",
        "Ho-oh",
        "Mime-jr",
        "Type-null",
        "Tapu-koko",
        "Tapu-lele",
        "Tapu-bulu",
        "Tapu-fini",
        "Mr-rime",
        "Great-tusk",
        "Scream-tail",
        "Brute-bonnet",
        "Flutter-mane",
        "Slither-wing",
        "Sandy-shocks",
        "Iron-treads",
        "Iron-bundle",
        "Iron-hands",
        "Iron-jugulis",
        "Iron-moth",
        "Iron-thorns",
        "Wo-chien",
        "Chien-pao",
        "Ting-lu",
        "Chi-yu",
        "Roaring-moon",
        "Iron-valiant",
        "Walking-wake",
        "Iron-leaves"
        )

        val nidoranF = "Nidoran♀"
        val nidoranM = "Nidoran♂"
        val mrMime = "Mr._Mime"
        val hoOh = "Ho-Oh"
        val mimeJr = "Mime_Jr."
        val typeNull = "Type:_Null"
        val tapuKoko = "Tapu_Koko"
        val tapuLele = "Tapu_Lele"
        val tapuBulu = "Tapu_Bulu"
        val tapuFini = "Tapu_Fini"
        val mrRime = "Mr._Rime"
        val greatTusk = "Great_Tusk"
        val screamTail = "Scream_Tail"
        val bruteBonnet = "Brute_Bonnet"
        val flutterMane = "Flutter_Mane"
        val slitherWing = "Slither_Wing"
        val sandyShocks = "Sandy_Shocks"
        val ironTreads = "Iron_Treads"
        val ironBundle = "Iron_Bundle"
        val ironHands = "Iron_Hands"
        val ironJugulis = "Iron_Jugulis"
        val ironMoth = "Iron_Moth"
        val ironThorns = "Iron_Thorns"
        val woChien = "Wo-Chien"
        val chienPao = "Chien-Pao"
        val tingLu = "Ting-Lu"
        val chiYu = "Chi-Yu"
        val roaringMoon = "Roaring_Moon"
        val ironValiant = "Iron_Valiant"
        val walkingWake = "Walking_Wake"
        val ironLeaves = "Iron_Leaves"
    }
    private fun loadBulbapediaPage(name: String, speciesName: String, varietyAmount: Int) {
        val wrongName = WrongNames()
        var changedName = ""
        var bulbapediaURL = ""
        Log.d("Bulbapedia top lvl", "name is $name spec is $speciesName var amt is $varietyAmount")
        if (varietyAmount >= 1 && name !in wrongName.wrongNamesList) {
            Log.d("bulbapedia if var amt", " species name is $speciesName")
            bulbapediaURL = getString(R.string.bulbapediaLink, speciesName)
        } else if (name in wrongName.wrongNamesList) {
            Log.d("bulbapedia wrong name", "name was $name")
            when (name) {
                "Nidoran-f" -> changedName = wrongName.nidoranF
                "Nidoran-m" -> changedName = wrongName.nidoranM
                "Mr-mime" -> changedName = wrongName.mrMime
                "Ho-oh" -> changedName = wrongName.hoOh
                "Mime-jr" -> changedName = wrongName.mimeJr
                "Type-null" -> changedName = wrongName.typeNull
                "Tapu-koko" -> changedName = wrongName.tapuKoko
                "Tapu-lele" -> changedName = wrongName.tapuLele
                "Tapu-bulu" -> changedName = wrongName.tapuBulu
                "Tapu-fini" -> changedName = wrongName.tapuFini
                "Mr-rime" -> changedName = wrongName.mrRime
                "Great-tusk" -> changedName = wrongName.greatTusk
                "Scream-tail" -> changedName = wrongName.screamTail
                "Brute-bonnet" -> changedName = wrongName.bruteBonnet
                "Flutter-mane" -> changedName = wrongName.flutterMane
                "Slither-wing" -> changedName = wrongName.slitherWing
                "Sandy-shocks" -> changedName = wrongName.sandyShocks
                "Iron-treads" -> changedName = wrongName.ironTreads
                "Iron-bundle" -> changedName = wrongName.ironBundle
                "Iron-hands" -> changedName = wrongName.ironHands
                "Iron-jugulis" -> changedName = wrongName.ironJugulis
                "Iron-moth" -> changedName = wrongName.ironMoth
                "Iron-thorns" -> changedName = wrongName.ironThorns
                "Wo-chien" -> changedName = wrongName.woChien
                "Chien-pao" -> changedName = wrongName.chienPao
                "Ting-lu" -> changedName = wrongName.tingLu
                "Chi-yu" -> changedName = wrongName.chiYu
                "Roaring-moon" -> changedName = wrongName.roaringMoon
                "Iron-valiant" -> changedName = wrongName.ironValiant
                "Walking-wake" -> changedName = wrongName.walkingWake
                "Iron-leaves" -> changedName = wrongName.ironLeaves
            }
            Log.d("bulbapedia wrong name", "name is now $changedName")
            bulbapediaURL = getString(R.string.bulbapediaLink, changedName)
        } else {
            Log.d("bulbapedia else","name is $name")
            bulbapediaURL = getString(R.string.bulbapediaLink, name)
        }
        Log.d("bulbapedia lastly", " url is $bulbapediaURL")
        binding.bulbapediaWv.loadUrl(bulbapediaURL)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun String?.upper(): String {
    var rest = this?.substring(1, this.length)
    var first = this?.substring(0, 1)
    return first?.uppercase() + rest
}

fun View.toggleVisibility() {
    this.visibility = if (this.visibility == View.VISIBLE) View.GONE else View.VISIBLE
}