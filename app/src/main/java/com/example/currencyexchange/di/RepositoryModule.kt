package com.example.currencyexchange.di

import com.example.currencyexchange.data.dataBaseRepository.*
import com.example.currencyexchange.data.networkRepository.*
import dagger.*

@Module
class RepositoryModule {
    @Provides
    fun provideNetworkRepository(api: ValutesApi): NetworkRepository = NetworkRepositoryImpl(api)

    @Provides
    fun provideValutesDataBaseRepo(dataBase: ValutesDataBase): ValutesDataBaseRepository =
        ValutesDataBaseRepositoryImpl(dataBase)
}