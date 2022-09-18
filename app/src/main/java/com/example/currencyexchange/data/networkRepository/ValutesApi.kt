package com.example.currencyexchange.data.networkRepository

import com.example.currencyexchange.data.model.InfoOfValutes
import retrofit2.http.GET

interface ValutesApi {
    @GET("daily_json.js")
    suspend fun getValutes(): InfoOfValutes
}