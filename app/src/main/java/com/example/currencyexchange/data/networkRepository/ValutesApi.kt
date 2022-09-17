package com.example.currencyexchange.data.networkRepository

import com.example.currencyexchange.data.model.InfoOfValutes
import retrofit2.Call
import retrofit2.http.GET

interface ValutesApi {
    @GET("daily_json.js")
    fun getValutes(): Call<InfoOfValutes>
}