package com.example.currencyexchange.data.dataBaseRepository

import com.example.currencyexchange.data.model.ValuteDB

interface ValutesDataBaseRepository {
    suspend fun getValutes(): List<ValuteDB>

    suspend fun getFavouriteId(id: String): String

    suspend fun insertValute(valuteDB: ValuteDB)

    suspend fun deleteValute(valuteDB: ValuteDB)
}