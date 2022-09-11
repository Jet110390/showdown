package com.example.showdown.di

import com.example.showdown.data.remote.services.ShowdownService
import com.example.showdown.utils.Constants.Companion.BASE_URL
import com.example.showdown.utils.Constants.Companion.TIMEOUT
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesMoshi() = Moshi.Builder().build()

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
//        val httpLoggingInterceptor =
//            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
//                .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder().connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
//            .addInterceptor(httpLoggingInterceptor)

    }

    @Provides
    @Singleton
    fun providesRetrofit(
        moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun providesShowdownService(retrofit: Retrofit): ShowdownService {
        return retrofit.create(ShowdownService::class.java)
    }
}