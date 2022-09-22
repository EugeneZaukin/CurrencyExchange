package com.example.currencyexchange.data.dataBaseRepository

import androidx.room.*
import com.example.currencyexchange.data.model.ValuteDB

@Database(entities = [ValuteDB::class], version = ValutesDataBase.VERSION)
abstract class ValutesDataBase : RoomDatabase() {
    abstract fun valutesDao(): ValuteDao

    companion object {
        const val NAME = "ValutesDB"
        const val VERSION = 2
    }
}