package com.example.currencyexchange.domain.usecases

import com.example.currencyexchange.domain.model.ValuteItem

interface GetValutesFromNetworkUseCase {
    suspend operator fun invoke(): List<ValuteItem>
}