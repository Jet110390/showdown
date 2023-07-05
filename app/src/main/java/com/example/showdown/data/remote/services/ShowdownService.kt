package com.example.showdown.data.remote.services

import com.example.showdown.data.remote.models.Urls
import com.example.showdown.data.remote.models.details.SpeciesInfoDto
import com.example.showdown.data.remote.models.stats.PokeInfoDto
import com.example.showdown.data.remote.models.stats.Species
import com.example.showdown.data.remote.models.urls.ResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowdownService {
    @GET("api/v2/pokemon")
    suspend fun getUrls(
        @Query("limit")limit:Int = 1010,
        @Query("offset")offset:Int = 0
    ): Response<ResponseDto>

    @GET("api/v2/pokemon/{name}")
    suspend fun getPokeInfo(
        @Path("name") name: String
    ): Response<PokeInfoDto>

    @GET("api/v2/pokemon/{dexNumber}")
    suspend fun getPokeInfoFromDexNumber(
        @Path("dexNumber") dexNumber: String
    ): Response<PokeInfoDto>

    @GET("api/v2/pokemon-species/{number}/")
    suspend fun getSpecies(
        @Path("number") number: Int
    ): Response<SpeciesInfoDto>

    @GET("api/v2/pokemon")
    suspend fun getFirstGenUrls(
        @Query("limit")limit:Int = 151,
        @Query("offset")offset:Int = 0
    ): Response<ResponseDto>

    @GET("api/v2/pokemon")
    suspend fun getSecondGenUrls(
        @Query("limit")limit:Int = 100,
        @Query("offset")offset:Int = 151
    ): Response<ResponseDto>

    @GET("api/v2/pokemon")
    suspend fun getThirdGenUrls(
        @Query("limit")limit:Int = 135,
        @Query("offset")offset:Int = 251
    ): Response<ResponseDto>

    @GET("api/v2/pokemon")
    suspend fun getFourthGenUrls(
        @Query("limit")limit:Int = 107,
        @Query("offset")offset:Int = 386
    ): Response<ResponseDto>

    @GET("api/v2/pokemon")
    suspend fun getFifthGenUrls(
        @Query("limit")limit:Int = 156,
        @Query("offset")offset:Int = 493
    ): Response<ResponseDto>

    @GET("api/v2/pokemon")
    suspend fun getSixthGenUrls(
        @Query("limit")limit:Int = 72,
        @Query("offset")offset:Int = 649
    ): Response<ResponseDto>

    @GET("api/v2/pokemon")
    suspend fun getSeventhGenUrls(
        @Query("limit")limit:Int = 88,
        @Query("offset")offset:Int = 721
    ): Response<ResponseDto>

    @GET("api/v2/pokemon")
    suspend fun getEighthGenUrls(
        @Query("limit")limit:Int = 89,
        @Query("offset")offset:Int = 809
    ): Response<ResponseDto>
}