package com.example.showdown.data.repository

import android.util.Log
import com.example.showdown.data.local.daos.FavPokemonDao
import com.example.showdown.data.local.daos.PokemonDao
import com.example.showdown.data.local.daos.VariantDao
import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.data.local.entities.Pokemon
import com.example.showdown.data.local.entities.Variant
import com.example.showdown.data.remote.models.DexEntry
import com.example.showdown.data.remote.models.SpeciesEntry
import com.example.showdown.data.remote.models.Urls
import com.example.showdown.data.remote.models.VarietyEntry
import com.example.showdown.data.remote.models.stats.PokeInfoDto
import com.example.showdown.data.remote.services.ShowdownService
import com.example.showdown.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonInfoRepository @Inject constructor(
    private val showdownService: ShowdownService,
    private val pokemonDao: PokemonDao,
    private val favPokeDao: FavPokemonDao,
    private val variantDao: VariantDao
) {

    //        var pokeList: MutableList<DexEntry> = mutableListOf()
//    val pokeList: MutableList<PokeInfoDto> = mutableListOf()
    suspend fun
            getPokeUrls(): List<Urls>? {
        return try {
            val response = showdownService.getUrls()

            if (response.isSuccessful) {
//                Log.d("getPokeUrls success","success: ${response.body()}")
                return response.body()?.results
            } else {
                Log.d("getPokeUrls error", "error: ${response.errorBody()}")
                emptyList()
            }
        } catch (ex: Exception) {
            Log.d("getPokeUrls exception", "cause: ${ex.cause}, $ex")
            emptyList()
        }
    }

    suspend fun getPokeInfoViaDexNumber(dexNumber: String): PokeInfoDto? {
        return try {
            val response = showdownService.getPokeInfoFromDexNumber(dexNumber)
            if (response.isSuccessful) {
                return response.body()
            } else {
                Log.d("getPokemonInfo error", "error: ${response.errorBody()}")
                null
            }
//            for (i in 0..905){
//                val response = showdownService.getPokeInfoFromDexNumber(i.toString())
//            }

        } catch (ex: Exception) {
            Log.d("getPokemonIn exception", "cause: ${ex.cause} ${ex.message}")
            null
        }
    }

    suspend fun getPokemonInfo(pokeInfo: Urls): PokeInfoDto? {

        return try {
//            val response1=showdownService.getPokeInfo(pokeInfo.name)
            val response = showdownService.getPokeInfo(pokeInfo.name)
            if (response.isSuccessful) {
//                Log.d("getPokemonInfo success ${response.body()?.order}","res: ${response.body()?.name}")
                return response.body()
            } else {
                Log.d("getPokemonInfo error", "error: ${response.errorBody()}")
                null
            }

        } catch (ex: Exception) {
            Log.d("getPokemonIn exception", "cause: ${ex.cause} ${ex.message}")
            null
        }
    }


    suspend fun getSpecies(pokeInfo: List<PokeInfoDto>): MutableList<SpeciesEntry> {
        val speciesList: MutableList<SpeciesEntry> = mutableListOf()
        val variantList: MutableList<VarietyEntry> = mutableListOf()
        val legendsImgs: MutableList<String> = mutableListOf(
            "https://i.ibb.co/G7w8zgn/qwilfish-hisui-official.png",
            "https://i.ibb.co/YyGMqbP/electrode-hisui-official.png",
            "https://i.ibb.co/V38HNcP/sneasel-hisui-official.png",
            "https://i.ibb.co/rG7HGJX/lilligant-hisui-official.png",
            "https://i.ibb.co/j53ZZnJ/basculin-white-striped-official.png",
            "https://i.ibb.co/TRHg6qf/sliggoo-hisui-official.png",
            "https://i.ibb.co/K0DccdJ/goodra-hisui-official.png",
            "https://i.ibb.co/0FJpc3H/avalugg-hisui-official.png",
            "https://i.ibb.co/2kF27n8/arcanine-hisui-sprite.png",
            "https://i.ibb.co/hmz8kPq/avalugg-hisui-sprite.png",
            "https://i.ibb.co/rMgNByq/basculin-white-striped-sprite.png",
            "https://i.ibb.co/h9YJnKL/braviary-hisui-sprite.png",
            "https://i.ibb.co/cbvfznh/decidueye-hisui-sprite.png",
            "https://i.ibb.co/v4DMQdY/dialga-origin-sprite.png",
            "https://i.ibb.co/nnhwS3t/electrode-hisui-sprite.png",
            "https://i.ibb.co/86CHtMs/goodra-hisui-sprite.png",
            "https://i.ibb.co/CsHFx9C/growlithe-hisui-sprite.png",
            "https://i.ibb.co/BcfDNdr/lilligant-hisui-sprite.png",
            "https://i.ibb.co/ZNqwTRD/arcanine-hisui-official.png",
            "https://i.ibb.co/YDngTNH/palkia-origin.png",
            "https://i.ibb.co/f1wt0Lq/decidueye-hisui-official.png",
            "https://i.ibb.co/DpLkX6R/dialga-origin-official.png",
            "https://i.ibb.co/VvcGDZx/palkia-origin-official.png",
            "https://i.ibb.co/gJwgHd5/braviary-hisui-official.png",
            "https://i.ibb.co/YQj6R8t/growlithe-hisui-official.png",
            "https://i.ibb.co/6DHwZsK/samurott-hisui-official.png",
            "https://i.ibb.co/VQGx9Bm/typhlosion-hisui-official.png",
            "https://i.ibb.co/Y3g0j4H/voltorb-hisui-official.png",
            "https://i.ibb.co/6YrsLqM/zoroark-hisui-official.png",
            "https://i.ibb.co/MgqC06Z/zorua-hisui-official.png",
            "https://i.ibb.co/b3KTHrj/qwilfish-hisui-sprite.png",
            "https://i.ibb.co/MpgvbYb/samurott-hisui-sprite.png",
            "https://i.ibb.co/BPJGMhv/sligoo-hisui-sprite.png",
            "https://i.ibb.co/R4r8mzg/sneasel-hisui-sprite.png",
            "https://i.ibb.co/P59x0Vn/typhlosion-hisui-sprite.png",
            "https://i.ibb.co/kDprYrY/voltorb-hisui-sprite.png",
            "https://i.ibb.co/rc9xFrQ/zoroark-hisui-sprite.png",
            "https://i.ibb.co/ZMCWygt/zorua-hisui-sprite.png",
            "https://i.ibb.co/GPDYjcf/sneasler-official.png",
            "https://i.ibb.co/68zPzWG/ursaluna-official.png",
            "https://i.ibb.co/2vYM2PX/overqwil-official.png",
            "https://i.ibb.co/BNDrw6b/enamorus-therian-official.png",
            "https://i.ibb.co/sRWsSgM/enamorus-incarnate-official.png",
            "https://i.ibb.co/RCVCNH1/basculegion-female-official.png",
            "https://i.ibb.co/Php9CR2/basculegion-female-sprite.png",
            "https://i.ibb.co/qrp8ZwS/basculegion-male-sprite.png",
            "https://i.ibb.co/7Vn3cVq/enamorus-incarnate-sprite.png",
            "https://i.ibb.co/HY4WmHp/enamorus-therian-sprite.png",
            "https://i.ibb.co/30jZW5B/kleavor-sprite.png",
            "https://i.ibb.co/Q89sGzQ/overqwil-sprite.png",
            "https://i.ibb.co/QCTwg6c/kleavor-official.png",
            "https://i.ibb.co/m5NgSWv/basculegion-male-official.png",
            "https://i.ibb.co/SPm1zhw/wyrdeer-official.png",
            "https://i.ibb.co/N7PptbG/sneasler-sprite.png",
            "https://i.ibb.co/94s2FfB/ursaluna-sprite.png",
            "https://i.ibb.co/BVhGW1f/wyrdeer-sprite.png",
        )
        try {
            for (i in 0..pokeInfo.size - 1) {
                val response1 = showdownService.getSpecies(pokeInfo[i].id)
                if (response1.isSuccessful) {
                    val speciesNumber = pokeInfo[i].species.url.replace(
                        "https://pokeapi.co/api/v2/pokemon-species/",
                        ""
                    ).takeWhile { it.isDigit() }.toInt()
                    var flavTextData =
                        response1.body()?.flavorTextEntries?.find { it.language.name.equals("en") }
                    val genus = response1.body()?.genera?.find { it.language.name.equals("en") }
//                   val genus = response1.body()?.genera?.get(7)?.genus
                    val evolvesFrom =
                        response1.body()?.evolvesFromSpecies?.name ?: "no pre-evolution"
                    val varieties = response1.body()?.varieties
//                   val growlitheResponse = showdownService.getPokeInfo("growlithe-hisui")
//                   for (i in 0..legendsImgs.size){
//                       if ("growlithe-hisui" in legendsImgs[i]){
//                           Log.d("imgUrl","${ legendsImgs[i]}")
//                       }
//                       else{Log.d("imgUrl","didnt work")}
//
//                   }
                    if (varieties?.size!! > 1) {
                        varieties.map {
//                           Log.d("variety","${it.pokemon}")
                            if (it.pokemon.name != varieties[0].pokemon.name + "-totem"
//                               && it.pokemon.name != varieties[0].pokemon.name+"-gmax"
                                && it.pokemon.name != varieties[0].pokemon.name + "-starter"
                                && it.pokemon.name.takeLast(3) != "cap"
                                && it.pokemon.name != varieties[0].pokemon.name
                                && it.pokemon.name != varieties[0].pokemon.name.replace(
                                    "-50",
                                    "-10"
                                )
                                && it.pokemon.name != varieties[0].pokemon.name + "-totem-disguised"
                                && it.pokemon.name != varieties[0].pokemon.name + "-totem-busted"
                                && it.pokemon.name != varieties[0].pokemon.name + "-gulping"
                                && it.pokemon.name != varieties[0].pokemon.name + "-gorging"
                                && it.pokemon.name != varieties[0].pokemon.name.replace(
                                    "-full-belly",
                                    "-hangry"
                                )
                                && it.pokemon.name != varieties[0].pokemon.name + "-eternamax"
                                && it.pokemon.name != varieties[0].pokemon.name + "-dada"
//                               && it.pokemon.name != varieties[0].pokemon.name + "-hisui"
                                && it.pokemon.name != varieties[0].pokemon.name + "-cosplay"
//                               && it.pokemon.name != varieties[0].pokemon.name + "-origin"
                                && it.pokemon.name != varieties[0].pokemon.name + "-original-cap"
//                               && it.pokemon.name != varieties[0].pokemon.name.replace(
//                                   "-red-striped",
//                                   "-white-striped"
//                               )
                            ) {
//                               better way maybe split these up??
                                val info = showdownService.getPokeInfo(it.pokemon.name)
//                               val getspecies = showdownService.getSpecies(info.body()?.id!!)
//                               var flavTextData=getspecies.body()?.flavorTextEntries?.find { it.language.name.equals("en")}
//                               val genus = getspecies.body()?.genera?.get(7)?.genus
//                               val evolvesFrom=getspecies.body()?.evolvesFromSpecies?.name ?: "no pre-evolution"
////                                Log.d("getSpecies success","${pokeInfo.name}")
//                               var species = SpeciesEntry(
//                                   flavTextData?.flavorText.toString(),
//                                   genus.toString(),
//                                   evolvesFrom)
//                    )

                                variantDao.addVariant(
                                    VarietyEntry(
                                        info.body()?.id!!,
                                        info.body()?.name!!,
//                                       info.body()?.sprites?.frontDefault!!
//                                       if(info.body()?.sprites?.frontDefault == null){
//                                            var url=""
//                                               (0..legendsImgs.size-1).forEach { i ->
//                                                   if (info.body()?.name!! + "-sprite" in legendsImgs[i]) {
////                                                       Log.d("success","${legendsImgs[i]}")
////                                                       legendsImgs[i]
//                                                       url=legendsImgs[i]
//                                                   }
//                                               }
//
//                                           url
//                                       }else{"${info.body()?.sprites?.frontDefault}"},
                                        info.body()?.sprites?.frontDefault,
                                        info.body()?.types?.get(0)?.type?.name,
                                        if (info.body()?.types?.size!! > 1) {
                                            info.body()?.types!![1]?.type?.name
                                        } else {
                                            "none"
                                        },
                                        info.body()?.height!!,
                                        info.body()?.weight!!,
                                        info.body()?.species?.url?.replace(
                                            "https://pokeapi.co/api/v2/pokemon-species/",
                                            ""
                                        )?.takeWhile { it.isDigit() }!!.toInt(),
//                                       if(info.body()?.sprites?.other?.officialArtwork?.frontDefault == null){
//                                           var url=""
//                                               (0..legendsImgs.size-1).forEach { i ->
//                                                   if (info.body()?.name!! + "-official"  in legendsImgs[i]) {
////                                                       Log.d("success","${legendsImgs[i]}")
//                                                       url=legendsImgs[i]
//                                                   }
//                                               }
//
//                                           url
//                                       }else{"${info.body()?.sprites?.other?.officialArtwork?.frontDefault}"}
                                        info.body()?.sprites?.other?.officialArtwork?.frontDefault,
                                    ).toVariant()
                                )

                                variantList.add(
                                    VarietyEntry(
                                        info.body()?.id!!,
                                        info.body()?.name!!,
//                                       if(info.body()?.sprites?.frontDefault == null){
//                                           var url = ""
//                                           (0..legendsImgs.size-1).forEach { i ->
//                                               if (info.body()?.name!! + "-sprite"  in legendsImgs[i]) {
////                                                   Log.d("success","${legendsImgs[i]}")
////                                                   legendsImgs[i]
//                                                   url=legendsImgs[i]
//                                               }
//                                           }
////                                           Log.d("sprite","${url}")
//                                           url
//                                       }else{"${info.body()?.sprites?.frontDefault}"},
                                        info.body()?.sprites?.frontDefault,
                                        info.body()?.types?.get(0)?.type?.name,
                                        if (info.body()?.types?.size!! > 1) {
                                            info.body()?.types!![1]?.type?.name
                                        } else {
                                            "none"
                                        },
                                        info.body()?.height!!,
                                        info.body()?.weight!!,
                                        info.body()?.species?.url?.replace(
                                            "https://pokeapi.co/api/v2/pokemon-species/",
                                            ""
                                        )?.takeWhile { it.isDigit() }!!.toInt(),
//                                       if(info.body()?.sprites?.other?.officialArtwork?.frontDefault == null){
//                                           var url=""
//                                               (0..legendsImgs.size-1).forEach { i ->
//                                                   if (info.body()?.name!! + "-official"  in legendsImgs[i]) {
////                                                       Log.d("success","${legendsImgs[i]}")
//                                                       url=legendsImgs[i]
//                                                   }
//                                               }
//
//                                           url
//                                       }else{"${info.body()?.sprites?.other?.officialArtwork?.frontDefault}"}
                                        info.body()?.sprites?.other?.officialArtwork?.frontDefault
                                    )
                                )
                                Log.d("getVariety", "${info.body()?.name}")
                            }
                        }
                    }
                    var species = SpeciesEntry(
                        flavTextData?.flavorText.toString(),
                        genus?.genus.toString(),
                        evolvesFrom,
                        variantList,
                        speciesNumber
                    )
                    speciesList.add(species)
                } else {
                    Log.d("getSpecies error", "${response1.message()}, ${response1.errorBody()}")
                    null
                }
            }
//             Log.d("growlithe response","${showdownService.getPokeInfo("growlithe-hisui")}")
//             speciesList[59].varieties.add(VarietyEntry(
//                 10230,
//                 "arcanine-hisui",
//                 "mipmap-hdpi/arcanine_hisui_sprite_foreground.png",
//                 "fire",
//                 "rock",
//                 20,
//                 1680,
//                 59,
//                 "mipmap-xxxhdpi/arcanine_hisui_official_foreground.png")
//             )
//             variantDao.addVariant(
//                 VarietyEntry(
//                     10230,
//                     "arcanine-hisui",
//                     "https://i.ibb.co/2kF27n8/Arcanine-hisui.png",
//                     "fire",
//                     "rock",
//                     20,
//                     1680,
//                     59,
//                     "https://archives.bulbagarden.net/media/upload/6/63/059Arcanine-Hisui.png"
////                     "mipmap-xxxhdpi/arcanine_hisui_official_foreground.png"
//                     ).toVariant()
//                 )
        } catch (ex: Exception) {
            Log.d("getSpecies exception", "cause: ${ex}")
            null
        }
        return speciesList
    }
//    suspend fun getSpecies(pokeInfo: Urls): SpeciesEntry? {
//        return try {
//            val response1=showdownService.getPokeInfo(pokeInfo.name)
//            val response2 = response1?.body()?.order?.let { showdownService.getSpecies(it) }
//            if(response2?.isSuccessful == true) {
////                Log.d("getSpecies success","res: ${response.body()?.name}")
//                var flavTextData=response2.body()?.flavorTextEntries?.find { it.language.name.equals("en")}
//                val genus = response2.body()?.genera?.get(7)?.genus
//                val evolvesFrom=response2.body()?.evolvesFromSpecies?.name ?: "no pre-evolution"
//                Log.d("getSpecies success","${pokeInfo.name}")
//                var species = SpeciesEntry(
//                    flavTextData?.flavorText.toString(),
//                    genus.toString(),
//                    evolvesFrom
//                    )
//                return species
//            }
//            else{
//                Log.d("getSpecies error","${response1.body()?.name} ${response1.body()?.order} $response2")
//                null
//            }
//
//        } catch (ex: Exception) {
//            Log.d("getSpecies exception","cause: ${ex.cause} ${ex.message}")
//            null
//        }
//    }

    suspend fun getPokedex(
        pokemonInfo: MutableList<PokeInfoDto>,
        speciesInfo: MutableList<SpeciesEntry>
    ): DataState<List<Pokemon>> {
        val isDbEmpty = pokemonDao.getPokemonCount() == 0
        var pokeList: MutableList<DexEntry> = mutableListOf()
        if (isDbEmpty) {

            val result = try {
//                 val response = showdownService.getUrls()
                var count = 0
                val legendsImgs: MutableList<String> = mutableListOf(
                    "https://i.ibb.co/G7w8zgn/qwilfish-hisui-official.png",
                    "https://i.ibb.co/YyGMqbP/electrode-hisui-official.png",
                    "https://i.ibb.co/V38HNcP/sneasel-hisui-official.png",
                    "https://i.ibb.co/rG7HGJX/lilligant-hisui-official.png",
                    "https://i.ibb.co/j53ZZnJ/basculin-white-striped-official.png",
                    "https://i.ibb.co/TRHg6qf/sliggoo-hisui-official.png",
                    "https://i.ibb.co/K0DccdJ/goodra-hisui-official.png",
                    "https://i.ibb.co/0FJpc3H/avalugg-hisui-official.png",
                    "https://i.ibb.co/2kF27n8/arcanine-hisui-sprite.png",
                    "https://i.ibb.co/hmz8kPq/avalugg-hisui-sprite.png",
                    "https://i.ibb.co/rMgNByq/basculin-white-striped-sprite.png",
                    "https://i.ibb.co/h9YJnKL/braviary-hisui-sprite.png",
                    "https://i.ibb.co/cbvfznh/decidueye-hisui-sprite.png",
                    "https://i.ibb.co/v4DMQdY/dialga-origin-sprite.png",
                    "https://i.ibb.co/nnhwS3t/electrode-hisui-sprite.png",
                    "https://i.ibb.co/86CHtMs/goodra-hisui-sprite.png",
                    "https://i.ibb.co/CsHFx9C/growlithe-hisui-sprite.png",
                    "https://i.ibb.co/BcfDNdr/lilligant-hisui-sprite.png",
                    "https://i.ibb.co/ZNqwTRD/arcanine-hisui-official.png",
                    "https://i.ibb.co/YDngTNH/palkia-origin.png",
                    "https://i.ibb.co/f1wt0Lq/decidueye-hisui-official.png",
                    "https://i.ibb.co/DpLkX6R/dialga-origin-official.png",
                    "https://i.ibb.co/VvcGDZx/palkia-origin-official.png",
                    "https://i.ibb.co/gJwgHd5/braviary-hisui-official.png",
                    "https://i.ibb.co/YQj6R8t/growlithe-hisui-official.png",
                    "https://i.ibb.co/6DHwZsK/samurott-hisui-official.png",
                    "https://i.ibb.co/VQGx9Bm/typhlosion-hisui-official.png",
                    "https://i.ibb.co/Y3g0j4H/voltorb-hisui-official.png",
                    "https://i.ibb.co/6YrsLqM/zoroark-hisui-official.png",
                    "https://i.ibb.co/MgqC06Z/zorua-hisui-official.png",
                    "https://i.ibb.co/b3KTHrj/qwilfish-hisui-sprite.png",
                    "https://i.ibb.co/MpgvbYb/samurott-hisui-sprite.png",
                    "https://i.ibb.co/BPJGMhv/sligoo-hisui-sprite.png",
                    "https://i.ibb.co/R4r8mzg/sneasel-hisui-sprite.png",
                    "https://i.ibb.co/P59x0Vn/typhlosion-hisui-sprite.png",
                    "https://i.ibb.co/kDprYrY/voltorb-hisui-sprite.png",
                    "https://i.ibb.co/rc9xFrQ/zoroark-hisui-sprite.png",
                    "https://i.ibb.co/ZMCWygt/zorua-hisui-sprite.png",
                    "https://i.ibb.co/GPDYjcf/sneasler-official.png",
                    "https://i.ibb.co/68zPzWG/ursaluna-official.png",
                    "https://i.ibb.co/2vYM2PX/overqwil-official.png",
                    "https://i.ibb.co/BNDrw6b/enamorus-therian-official.png",
                    "https://i.ibb.co/sRWsSgM/enamorus-incarnate-official.png",
                    "https://i.ibb.co/RCVCNH1/basculegion-female-official.png",
                    "https://i.ibb.co/Php9CR2/basculegion-female-sprite.png",
                    "https://i.ibb.co/qrp8ZwS/basculegion-male-sprite.png",
                    "https://i.ibb.co/7Vn3cVq/enamorus-incarnate-sprite.png",
                    "https://i.ibb.co/HY4WmHp/enamorus-therian-sprite.png",
                    "https://i.ibb.co/30jZW5B/kleavor-sprite.png",
                    "https://i.ibb.co/Q89sGzQ/overqwil-sprite.png",
                    "https://i.ibb.co/QCTwg6c/kleavor-official.png",
                    "https://i.ibb.co/m5NgSWv/basculegion-male-official.png",
                    "https://i.ibb.co/SPm1zhw/wyrdeer-official.png",
                    "https://i.ibb.co/N7PptbG/sneasler-sprite.png",
                    "https://i.ibb.co/94s2FfB/ursaluna-sprite.png",
                    "https://i.ibb.co/BVhGW1f/wyrdeer-sprite.png",
                )
                for (i in 0 until pokemonInfo.size) {
//                    if(i>=904){
//                     Log.d("get pokedex at i=$i"," spec= ${speciesInfo[i]} info=${pokemonInfo[i].name}")}
                    pokeList.add(
                        DexEntry(
                            pokemonInfo[i].id,
                            pokemonInfo[i].name,
                            pokemonInfo[i].sprites?.frontDefault!!,
//                         if(pokemonInfo[i].sprites?.frontDefault == null){
//                             var url = ""
//                             (0..legendsImgs.size-1).forEach { d ->
//                                 if (pokemonInfo[i].name + "-sprite"  in legendsImgs[d]) {
//                                     Log.d("sprite success","${legendsImgs[d]}")
////                                                   legendsImgs[i]
//                                     url=legendsImgs[d]
//                                 }
//                             }
//                             Log.d("image","${url}")
//                             url
//                         }else{"${pokemonInfo[i].sprites?.frontDefault}"},
                            pokemonInfo[i].types[0]?.type?.name,
                            if (pokemonInfo[i].types.size > 1) {
                                pokemonInfo[i].types[1]?.type?.name
                            } else {
                                "none"
                            },
                            pokemonInfo[i].height,
                            pokemonInfo[i].weight,
                            pokemonInfo[i].stats[0].baseStat,
                            pokemonInfo[i].stats[1].baseStat,
                            pokemonInfo[i].stats[2].baseStat,
                            pokemonInfo[i].stats[3].baseStat,
                            pokemonInfo[i].stats[4].baseStat,
                            pokemonInfo[i].stats[5].baseStat,
                            speciesInfo[i],
//                         if(pokemonInfo[i].sprites?.other?.officialArtwork?.frontDefault == null){
//                             var url = ""
//                             (0..legendsImgs.size-1).forEach { d ->
//                                 if (pokemonInfo[i].name + "-official"  in legendsImgs[d]) {
//                                                   Log.d("official success","${legendsImgs[d]}")
////                                                   legendsImgs[i]
//                                     url=legendsImgs[d]
//                                 }
//                             }
////                             Log.d("sprite","${url}")
//                             url
//                         }else{"${pokemonInfo[i].sprites?.other?.officialArtwork?.frontDefault}"}
                            pokemonInfo[i].sprites?.other?.officialArtwork?.frontDefault
//                         pokemonInfo[i].sprites.frontShiny
                        )
                    )

//                     Log.d("count","${count} ${speciesInfo.size}")
                    count++

                }
//                 var variants: MutableList<VarietyEntry> = mutableListOf()
//                 pokeList.add(
//                     DexEntry(
//                         899,
//                         "wyrdeer",
//                         "https://i.ibb.co/BVhGW1f/wyrdeer.png",
//                         "normal",
//                         "psychic",
//                         18,
//                         951,
//                         103,
//                         105,
//                         72,
//                         105,
//                         75,
//                         65,
//                         SpeciesEntry(
//                             "The black orbs shine with an uncanny light when the Pokémon is erecting invisible barriers. The fur shed from its beard retains heat well and is a highly useful material for winter clothing.",
//                             "Big Horn Pokémon",
//                             "stantler",
//                             variants,
//                             899),
//                         "https://archives.bulbagarden.net/media/upload/7/75/899Wyrdeer.png"
////                         "mipmap-xxxhdpi/wyrdeer_official_foreground.png"
//                     )
//                 )

//                 Log.d("getPokedex success","res: ${pokeList}")
                if (pokeList.size != null) {
                    val dexList = pokeList.map {
//                         Log.d("getPokedex success","res: ${it.name}")
                        it.toPokemon()
//
                    }
//                Log.d("dexlist","$dexList")
                    pokemonDao.insertPokemon(dexList)
                    DataState.Success(dexList)
                } else {
                    DataState.Error("Could not fetch any pokemon")
                }

            } catch (ex: Exception) {
                Log.d("heres the error", "${ex.message} & ${ex.cause} exception: $ex")
                DataState.Error(ex.message ?: "Unexpected error.")
            }
//             Log.d("result","pokelist $pokeList")
            return result
//             emit(result)
        } else {
            // If db not empty return db contents
            val dbResult = pokemonDao.getAllPokemon()
//             Log.d("getPokedex success","List is ${dbResult.size} long")
//             emit(DataState.Success(dbResult))
            return DataState.Success(dbResult)
        }
    }

    fun getGenerationOne(): List<Pokemon> {
        return pokemonDao.getGenOne()
    }
    fun getGenerationTwo():List<Pokemon>{
        return pokemonDao.getGenTwo()
    }
    fun getGenerationThree():List<Pokemon>{
        return pokemonDao.getGenThree()
    }
    fun getGenerationFour():List<Pokemon>{
        return pokemonDao.getGenFour()
    }
    fun getGenerationFive():List<Pokemon>{
        return pokemonDao.getGenFive()
    }
    fun getGenerationSix():List<Pokemon>{
        return pokemonDao.getGenSix()
    }
    fun getGenerationSeven():List<Pokemon>{
        return pokemonDao.getGenSeven()
    }
    fun getGenerationEight():List<Pokemon>{
        return pokemonDao.getGenEight()
    }

    fun getGenerationNine():List<Pokemon>{
        return pokemonDao.getGenNine()
    }
    fun fullDexList():List<Pokemon> {
        return pokemonDao.getFullDex()
    }

    fun getOfficialImg(id: Int): String{
        return pokemonDao.getOfficialImg(id)
    }
    suspend fun addToFavs(pokemon: Pokemon): Int {
//        val dbEmpty = favPokeDao.getFavPokemonCount() == 0
        val favPoke: FavoritePokemon = pokemon.toFavPokemon()
//        if(favPokeDao.exists(favPoke.id)){
//            favPokeDao.removePokemonFromFavs(favPoke)

//        }else{
        pokemonDao.addPokemonToFavs(favPoke)
//        }
        Log.d("added", "${favPoke.name}")
        return favPoke.id
    }

    fun getVariants(pokemon: Pokemon): List<Variant> {

        return variantDao.getVariants(pokemon.speciesNumber!!)
    }


//
//    suspend fun removeFromFavs(pokemon: FavoritePokemon): Int {
//        favPokeDao.removePokemonFromFavs(pokemon)
//        return pokemon.id
//    }

}