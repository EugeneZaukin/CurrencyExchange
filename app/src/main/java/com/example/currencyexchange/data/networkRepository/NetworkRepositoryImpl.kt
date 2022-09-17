package com.example.currencyexchange.data.networkRepository

import com.example.currencyexchange.data.model.InfoOfValutes
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkRepositoryImpl {

     fun getValutes(callback: Callback<InfoOfValutes>) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.cbr-xml-daily.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ValutesApi::class.java)

        retrofit.getValutes().enqueue(callback)
    }

}