package com.example.currencyexchange.data.dataBaseRepository

import com.example.currencyexchange.data.model.ValuteDB

class ValutesDataBaseRepositoryImpl(private val dataBase: ValutesDataBase) : ValutesDataBaseRepository {
    override suspend fun getValutes(): List<ValuteDB> = dataBase.valutesDao().getValutes()

    override suspend fun getFavouriteId(id: String): String =
        dataBase.valutesDao().getFavouriteId(id)

    override suspend fun insertValute(valuteDB: ValuteDB) {
        dataBase.valutesDao().insertValute(valuteDB)
    }

    override suspend fun deleteValute(valuteDB: ValuteDB) {
        dataBase.valutesDao().deleteValute(valuteDB)
    }
}