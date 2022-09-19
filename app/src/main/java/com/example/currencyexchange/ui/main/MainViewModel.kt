package com.example.currencyexchange.ui.main

import androidx.lifecycle.*
import com.example.currencyexchange.data.dataBaseRepository.ValutesDataBaseRepository
import com.example.currencyexchange.domain.model.ValuteItem
import com.example.currencyexchange.domain.model.usecases.GetValutesFromNetworkUseCase
import com.example.currencyexchange.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val networkUseCase: GetValutesFromNetworkUseCase,
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

    init {
        emitPopularValutes()
    }

    private fun emitPopularValutes() {
        _loadingState.tryEmit(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val valutes = networkUseCase()
                val idsFromDB = getFavouritesValutes().map { it.id }

                val valutesMap = valutes.map { valute ->
                    val isFavourite = idsFromDB.contains(valute.id)
                    valute.copy(isFavourite = isFavourite)
                }

                _valutesState.tryEmit(valutesMap)
                _loadingState.tryEmit(false)
            } catch (e: Exception) {
                _errorLoad.tryEmit(true)
            }
        }
    }

    fun onClickSort(position: Int) {
        val valutes = _valutesState.value

        val sortedList = when(position) {
            0 -> valutes.shuffled()
            1 -> {
                val comparatorNameAsc =
                    Comparator<ValuteItem> { v1, v2 -> v1.name.compareTo(v2.name) }
                valutes.sortedWith(comparatorNameAsc)
            }
            2 -> {
                val comparatorNameDesc =
                    Comparator<ValuteItem> { v1, v2 -> v2.name.compareTo(v1.name) }
                valutes.sortedWith(comparatorNameDesc)
            }
            3 -> {
                val comparatorValueAsc =
                    Comparator<ValuteItem> { v1, v2 -> v1.value.compareTo(v2.value) }
                valutes.sortedWith(comparatorValueAsc)
            }
            4 -> {
                val comparatorValueDesc =
                    Comparator<ValuteItem> { v1, v2 -> v2.value.compareTo(v1.value) }
                valutes.sortedWith(comparatorValueDesc)
            }
            else -> valutes.shuffled()
        }

        _valutesState.tryEmit(sortedList)
    }

    fun onClickPopular() {
        if (btnPopularState.value) return
        _btnPopularState.tryEmit(true)
        _btnFavouritesState.tryEmit(false)
        emitPopularValutes()
        _favouritesScreenState.tryEmit(false)
    }

    fun onClickFavourites() {
        if (btnFavouritesState.value) return
        _btnPopularState.tryEmit(false)
        _btnFavouritesState.tryEmit(true)
        emitFavouritesValutes()
    }

    private fun emitFavouritesValutes() {
        viewModelScope.launch(Dispatchers.IO) {
            val valutes = getFavouritesValutes()

            if (valutes.isEmpty()) {
                _valutesState.tryEmit(listOf())
                _favouritesScreenState.tryEmit(true)
                return@launch
            }
            _valutesState.tryEmit(valutes)
            _favouritesScreenState.tryEmit(false)
        }
    }

    private suspend fun getFavouritesValutes(): List<ValuteItem> =
        try {
            val valutes = valutesDataBase.getValutes()
            if (valutes.isEmpty())
                listOf()
            else
                valutes.map { it.toFavouriteValuteItem() }
        } catch (e: Exception) {
            listOf()
        }

    fun onValuteClick(valute: ValuteItem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val id = getFavouriteId(valute.id)
                if (id == null || id.isEmpty())
                    addToFavourites(valute)
                else
                    deleteFromFavourites(valute)
            } catch (e: Exception) {

            }
        }
    }

    private suspend fun getFavouriteId(id: String): String? =
        try {
            valutesDataBase.getFavouriteId(id)
        } catch (e: Exception) {
            null
        }

    private suspend fun addToFavourites(valute: ValuteItem) {
        try {
            valutesDataBase.insertValute(valute.toValuteDB())
            updatePopularList(valute, true)
        } catch (e: Exception) {

        }
    }

    private suspend fun deleteFromFavourites(valute: ValuteItem) {
        try {
            valutesDataBase.deleteValute(valute.toValuteDB())

            if (btnFavouritesState.value)
                emitFavouritesValutes()
            else
                updatePopularList(valute, false)
        } catch (e: Exception) {

        }
    }

    private fun updatePopularList(valute: ValuteItem, isFavourites: Boolean) {
        val updateList = valutesState.value.map {
            if (it.id == valute.id)
                it.copy(isFavourite = isFavourites)
            else
                it
        }
        _valutesState.tryEmit(updateList)
    }
}