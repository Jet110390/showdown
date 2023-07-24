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

    suspend fun getPokemonInfo(name: String): PokeInfoDto? {

        return try {
//            val response1=showdownService.getPokeInfo(pokeInfo.name)
            val response = showdownService.getPokeInfo(name)
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

    private suspend fun getSpeciesData(pokeInfo: PokeInfoDto): SpeciesEntry{
        val variantList: MutableList<VarietyEntry> = mutableListOf()
        var species = SpeciesEntry(
            "",
            "",
            "",
            variantList,
            0,
            "",
            0
        )
    var variant = ""

        try {
            val response1= showdownService.getSpecies(pokeInfo.id)
            if (response1.isSuccessful) {
                val speciesNumber = pokeInfo.species.url.replace(
                    "https://pokeapi.co/api/v2/pokemon-species/",
                    ""
                ).takeWhile { it.isDigit() }.toInt()
                var flavTextData =
                    response1.body()?.flavorTextEntries?.find { it.language.name.equals("en") }
                val genus = response1.body()?.genera?.find { it.language.name.equals("en") }
                val evolvesFrom =
                    response1.body()?.evolvesFromSpecies?.name ?: "no pre-evolution"
                val varieties = response1.body()?.varieties
                val speciesName = response1.body()?.name
                val varietyAmount = response1.body()?.varieties?.size?.minus(1)

                if (varieties?.size!! > 1) {
                    varieties.map {
                        if (!it.isDefault
                            && it.pokemon.name != varieties[0].pokemon.name + "-totem"
                            && it.pokemon.name != varieties[0].pokemon.name + "-totem-alola"
                            && it.pokemon.name != varieties[0].pokemon.name + "-starter"
                            && it.pokemon.name.takeLast(3) != "cap"
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
                            && it.pokemon.name != varieties[0].pokemon.name + "-cosplay"
                            && it.pokemon.name != varieties[0].pokemon.name + "-original-cap"
                            && it.pokemon.name != varieties[0].pokemon.name + "-own-tempo"
                            && it.pokemon.name != varieties[0].pokemon.name + "-battle-bond"
                            && it.pokemon.name != varieties[0].pokemon.name + "-limited-build"
                            && it.pokemon.name != varieties[0].pokemon.name + "-sprinting-build"
                            && it.pokemon.name != varieties[0].pokemon.name + "-swimming-build"
                            && it.pokemon.name != varieties[0].pokemon.name + "-gliding-build"
                            && it.pokemon.name != varieties[0].pokemon.name + "-low-power-mode"
                            && it.pokemon.name != varieties[0].pokemon.name + "-drive-mode"
                            && it.pokemon.name != varieties[0].pokemon.name + "-aquatic-mode"
                            && it.pokemon.name != varieties[0].pokemon.name + "-glide-mode"
                        ) {
                            val info = getPokemonInfo(it.pokemon.name)
                            Log.d("species info", "${it.pokemon.name}")
                            variant = it.pokemon.name

                            variantDao.addVariant(
                                VarietyEntry(
                                    info?.id!!,
                                    info?.name!!,
                                    info?.sprites?.frontDefault,
                                    info?.types[0]?.type?.name,
                                    if(info.types.size>1){
                                    info?.types[1]?.type?.name
                                    }else{"none"},
                                    info?.height!!,
                                    info?.weight!!,
                                    speciesNumber,
                                    info?.sprites?.other?.officialArtwork?.frontDefault,
                                    speciesName
                                ).toVariant()
                            )

                            variantList.add(
                                VarietyEntry(
                                    info?.id!!,
                                    info?.name!!,
                                    info?.sprites?.frontDefault,
                                    info?.types[0]?.type?.name,
                                    if(info.types.size>1){
                                        info?.types[1]?.type?.name
                                    }else{"none"},
                                    info?.height!!,
                                    info?.weight!!,
                                    speciesNumber,
                                    info?.sprites?.other?.officialArtwork?.frontDefault,
                                    speciesName
                                )
                            )
//                            form(rotom), divergent(moltres-g), mega, gmax, version(zacian-crowned), alternate(urshifu-rapd strike)
                        }
                    }
                }
                species = SpeciesEntry(
                    flavTextData?.flavorText.toString(),
                    genus?.genus.toString(),
                    evolvesFrom,
                    variantList,
                    speciesNumber,
                    speciesName,
                    varietyAmount
                )
            }
        }
        catch (ex: Exception){
            Log.d("getSpecies exception", "${variant} failed because: ${ex}")
            null
        }
        return species
    }

    private fun getPokedexEntry(
        pokemonInfo: PokeInfoDto,
        speciesInfo: SpeciesEntry
    ): Pokemon {
        var pokemon = Pokemon(0, "", "", "", "", 0, 0, "", 0, 0, 0, 0, 0, 0, "", "", "", 0,"",0)
        try {
            pokemon = DexEntry(
                pokemonInfo.id,
                pokemonInfo.name,
                pokemonInfo.sprites?.frontDefault!!,
                pokemonInfo.types[0]?.type?.name,
                if (pokemonInfo.types.size > 1) {
                    pokemonInfo.types[1]?.type?.name
                } else {
                    "none"
                },
                pokemonInfo.height,
                pokemonInfo.weight,
                pokemonInfo.stats[0].baseStat,
                pokemonInfo.stats[1].baseStat,
                pokemonInfo.stats[2].baseStat,
                pokemonInfo.stats[3].baseStat,
                pokemonInfo.stats[4].baseStat,
                pokemonInfo.stats[5].baseStat,
                speciesInfo,
                pokemonInfo.sprites?.other?.officialArtwork?.frontDefault
            ).toPokemon()


        } catch (ex: Exception) {
            Log.d("heres the error", "${ex.message} & ${ex.cause} exception: $ex")
        }
        return pokemon
    }

    suspend fun getPokedexList2(): DataState<List<Pokemon>> {
        val isDbEmpty = pokemonDao.getPokemonCount() == 0
        val dex: MutableList<Pokemon> = mutableListOf()
        val currentPokeAmount = 1010
        val fullDex = pokemonDao.getPokemonCount() == currentPokeAmount
        if (isDbEmpty || !fullDex) {
            val result = try {
                for (i in 1..currentPokeAmount) {
                    val response = getPokeInfoViaDexNumber(i.toString())
                    response.let { poke ->
                        dex.add(getPokedexEntry(poke!!, getSpeciesData(poke)))
                    }
                }
                if (dex.size != null) {
                    pokemonDao.upsertPokemonList(dex)
                    DataState.Success(dex)
                } else {
                    DataState.Error("Could not fetch any pokemon")
                }
            } catch (ex: Exception) {
                Log.d("here's the error", "${ex.message} & ${ex.cause} exception: $ex")
                DataState.Error(ex.message ?: "Unexpected error.")
            }
            return result
        } else {
            // If db not empty return db contents
            val dbResult = pokemonDao.getAllPokemon()
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