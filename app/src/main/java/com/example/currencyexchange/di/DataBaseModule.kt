package com.example.currencyexchange.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.example.currencyexchange.data.dataBaseRepository.ValutesDataBase
import dagger.*

@Module
class DataBaseModule {
    @Provides
    fun provideValutesDatabase(context: Context, migration: Migration): ValutesDataBase = Room.databaseBuilder(
        context.applicationContext,
        ValutesDataBase::class.java,
        ValutesDataBase.NAME
    )
        .addMigrations(migration)
        .build()
}