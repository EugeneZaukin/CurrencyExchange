package com.example.currencyexchange.ui.main

import androidx.lifecycle.*
import javax.inject.*

class MainViewModelFactory @Inject constructor(
    viewModelProvider: Provider<MainViewModel>
): ViewModelProvider.Factory {
    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        MainViewModel::class.java to viewModelProvider
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}