package com.example.currencyexchange

import android.app.Application
import com.example.currencyexchange.di.AppComponent
import com.example.currencyexchange.di.DaggerAppComponent

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