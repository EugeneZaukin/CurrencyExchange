package com.example.currencyexchange.di

import com.example.currencyexchange.data.networkRepository.*
import dagger.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    @Provides
    fun gsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun retrofit(gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://www.cbr-xml-daily.ru/")
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    fun api(retrofit: Retrofit): ValutesApi = retrofit.create(ValutesApi::class.java)
}