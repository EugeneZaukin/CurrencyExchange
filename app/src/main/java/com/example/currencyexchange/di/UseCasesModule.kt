package com.example.currencyexchange.di

import com.example.currencyexchange.data.networkRepository.NetworkRepository
import com.example.currencyexchange.domain.model.usecases.GetValutesFromNetworkUseCase
import com.example.currencyexchange.domain.model.usecases.GetValutesFromNetworkUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun provideGetValutesFromNetworkUseCase(networkRepository: NetworkRepository): GetValutesFromNetworkUseCase =
        GetValutesFromNetworkUseCaseImpl(networkRepository)
}