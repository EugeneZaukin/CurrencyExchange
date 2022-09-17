package com.example.currencyexchange.ui.main

import androidx.lifecycle.*
import com.example.currencyexchange.data.networkRepository.NetworkRepositoryImpl
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    fun getValutes() {
        val networkRepositoryImpl = NetworkRepositoryImpl()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val valutes = networkRepositoryImpl.getValutes()

            } catch (e: Exception) {

            }
        }
    }
}