package com.example.currencyexchange.ui.main

import androidx.lifecycle.*
import com.example.currencyexchange.data.networkRepository.NetworkRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _btnPopularState = MutableStateFlow(true)
    val btnPopularState get() = _btnPopularState.asStateFlow()

    private val _btnFavouritesState = MutableStateFlow(false)
    val btnFavouritesState get() = _btnFavouritesState.asStateFlow()

    private val _errorLoad = MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)
    val errorLoad get() = _errorLoad.asSharedFlow()

    fun getValutes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val valutes = networkRepository.getValutes()

            } catch (e: Exception) {
                _errorLoad.tryEmit(true)
            }
        }
    }

    fun onClickPopular() {
        if (_btnPopularState.value) return
        _btnPopularState.tryEmit(true)
        _btnFavouritesState.tryEmit(false)
    }

    fun onClickFavourites() {
        if (_btnFavouritesState.value) return
        _btnPopularState.tryEmit(false)
        _btnFavouritesState.tryEmit(true)

    }
}