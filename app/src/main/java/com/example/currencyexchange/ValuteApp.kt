package com.example.currencyexchange

import android.app.Application
import android.content.Context
import com.example.currencyexchange.di.*

class ValuteApp : Application() {
    private lateinit var _appComponent: AppComponent
    val appComponent get() = _appComponent

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when(this) {
        is ValuteApp -> appComponent
        else -> this.applicationContext.appComponent
    }