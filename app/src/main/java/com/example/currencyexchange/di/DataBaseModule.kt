package com.example.currencyexchange.di

import android.content.Context
import androidx.room.Room
import com.example.currencyexchange.data.dataBaseRepository.ValutesDataBase
import dagger.*

@Module
class DataBaseModule {
    @Provides
    fun provideValutesDatabase(context: Context): ValutesDataBase = Room.databaseBuilder(
        context.applicationContext,
        ValutesDataBase::class.java,
        ValutesDataBase.NAME
    ).build()
}