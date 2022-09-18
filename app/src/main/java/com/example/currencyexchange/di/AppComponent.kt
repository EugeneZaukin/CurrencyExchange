package com.example.currencyexchange.di

import android.content.Context
import dagger.*

@Component
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}