package com.example.currencyexchange.data.networkRepository

import com.example.currencyexchange.data.model.InfoOfValutes

interface NetworkRepository {
    suspend fun getValutes(): InfoOfValutes
}