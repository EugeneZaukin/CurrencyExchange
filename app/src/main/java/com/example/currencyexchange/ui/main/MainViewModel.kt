package com.example.currencyexchange.ui.main

import androidx.lifecycle.*
import com.example.currencyexchange.data.dataBaseRepository.ValutesDataBaseRepository
import com.example.currencyexchange.data.model.ValuteDB
import com.example.currencyexchange.data.networkRepository.NetworkRepository
import com.example.currencyexchange.domain.model.ValuteItem
import com.example.currencyexchange.utils.roundToTwoCharacters
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val valutesDataBase: ValutesDataBaseRepository
) : ViewModel() {
    private val _btnPopularState = MutableStateFlow(true)
    val btnPopularState get() = _btnPopularState.asStateFlow()

    private val _btnFavouritesState = MutableStateFlow(false)
    val btnFavouritesState get() = _btnFavouritesState.asStateFlow()

    private val _valutesState = MutableStateFlow(listOf<ValuteItem>())
    val valutesState get() = _valutesState.asStateFlow()

    private val _loadingState = MutableStateFlow(true)
    val loadingState get() = _loadingState.asStateFlow()

    private val _favouritesScreenState = MutableStateFlow(false)
    val favouritesScreenState get() = _favouritesScreenState.asStateFlow()

    private val _errorLoad = MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)
    val errorLoad get() = _errorLoad.asSharedFlow()

    fun getValutes() {
        _loadingState.tryEmit(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val valutes = networkRepository.getValutes()

                val valutesMap = valutes.valutes.values.map {
                    ValuteItem(it.id, it.charCode, it.name, it.value.roundToTwoCharacters())
                }

                _valutesState.tryEmit(valutesMap)
                _loadingState.tryEmit(false)
            } catch (e: Exception) {
                _errorLoad.tryEmit(true)
            }
        }
    }

    fun onClickPopular() {
        if (_btnPopularState.value) return
        _btnPopularState.tryEmit(true)
        _btnFavouritesState.tryEmit(false)

        getValutes()
        _favouritesScreenState.tryEmit(false)
    }

    fun onClickFavourites() {
        if (_btnFavouritesState.value) return
        _btnPopularState.tryEmit(false)
        _btnFavouritesState.tryEmit(true)
        getFavouritesValutes()
    }

    private fun getFavouritesValutes() {
        viewModelScope.launch {
            try {
                val valutes = valutesDataBase.getValutes()

                if (valutes.isEmpty()) {
                    _valutesState.tryEmit(listOf())
                    _favouritesScreenState.tryEmit(true)
                    return@launch
                }

                val map = valutes.map { ValuteItem(it.idServer, it.charCode, it.name, it.value) }
                _valutesState.tryEmit(map)
                _favouritesScreenState.tryEmit(false)
            } catch (e: Exception) {

            }
        }
    }

    fun onValuteClick(valute: ValuteItem) {
        val valuteDB = ValuteDB(
            idServer = valute.id,
            name = valute.name,
            charCode = valute.charCode,
            value = valute.value
        )

        viewModelScope.launch {
            try {
                valutesDataBase.insertValute(valuteDB)
            } catch (e: Exception) {

            }
        }
    }
}