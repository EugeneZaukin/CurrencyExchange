package com.example.currencyexchange.domain.model

data class ValuteItem(
    val id: String,
    val charCode:String,
    val name: String,
    val nominal: Int,
    val value: Double,
    val isFavourite: Boolean
)