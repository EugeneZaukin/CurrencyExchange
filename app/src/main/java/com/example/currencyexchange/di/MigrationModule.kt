package com.example.currencyexchange.di

import androidx.room.migration.Migration
import com.example.currencyexchange.data.dataBaseRepository.migration.MigrationFrom1To2
import dagger.*

@Module
interface MigrationModule {
    @Binds
    fun provideMigrationFrom1To2(migration: MigrationFrom1To2): Migration
}