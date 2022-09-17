package com.example.currencyexchange.data.model

import com.google.gson.annotations.SerializedName

data class InfoOfValutes(
    @SerializedName("Date")
    val date: String,

    @SerializedName("Timestamp")
    val timestamp: String,

    @SerializedName("Valute")
    val valutes: Map<String, Valute>
)