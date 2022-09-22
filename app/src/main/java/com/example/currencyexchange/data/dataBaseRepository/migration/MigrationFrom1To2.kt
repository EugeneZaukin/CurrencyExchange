package com.example.currencyexchange.data.dataBaseRepository.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.inject.Inject

class MigrationFrom1To2 @Inject constructor() : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Valute ADD COLUMN nominal INT DEFAULT 0 NOT NULL")
    }
}