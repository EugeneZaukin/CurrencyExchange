package com.example.currencyexchange.data.dataBaseRepository

import androidx.room.*
import com.example.currencyexchange.data.model.ValuteDB

@Dao
interface ValuteDao {
    @Query("SELECT * FROM Valute")
    suspend fun getValutes(): List<ValuteDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertValute(valuteDB: ValuteDB)

    @Delete
    suspend fun deleteValute(valuteDB: ValuteDB)
}