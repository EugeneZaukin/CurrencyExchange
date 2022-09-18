package com.example.currencyexchange.data.networkRepository

import com.example.currencyexchange.data.model.InfoOfValutes

class NetworkRepositoryImpl(private val valutesApi: ValutesApi) : NetworkRepository {

    override suspend fun getValutes(): InfoOfValutes = valutesApi.getValutes()
}