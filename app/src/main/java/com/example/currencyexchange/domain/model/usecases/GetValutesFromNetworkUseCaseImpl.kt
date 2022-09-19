package com.example.currencyexchange.domain.model.usecases

import com.example.currencyexchange.data.networkRepository.NetworkRepository
import com.example.currencyexchange.domain.model.ValuteItem
import com.example.currencyexchange.utils.toValuteItem

class GetValutesFromNetworkUseCaseImpl(
    private val networkRepository: NetworkRepository
) : GetValutesFromNetworkUseCase {
    override suspend fun invoke(): List<ValuteItem> =
        networkRepository.getValutes().valutes.values.map { it.toValuteItem() }
}