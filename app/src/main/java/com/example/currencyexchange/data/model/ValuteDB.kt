package com.example.currencyexchange.data.model

import androidx.room.*

@Entity(tableName = "Valute")
data class ValuteDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idServer: String,
    val charCode:String,
    val name: String,
    val value: Double
)