package com.example.currencyexchange.di

import android.content.Context
import com.example.currencyexchange.ui.main.MainViewModelFactory
import dagger.*

@Component(modules = [NetworkModule::class, DataBaseModule::class, RepositoryModule::class, UseCasesModule::class])
interface AppComponent {
    fun mainViewModelFactory(): MainViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}