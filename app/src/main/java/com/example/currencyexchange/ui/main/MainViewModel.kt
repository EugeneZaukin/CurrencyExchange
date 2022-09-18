package com.example.currencyexchange.ui.main

import androidx.lifecycle.*
import com.example.currencyexchange.data.networkRepository.NetworkRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {
    fun getValutes() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val valutes = networkRepository.getValutes()

            } catch (e: Exception) {

            }
        }
    }
}