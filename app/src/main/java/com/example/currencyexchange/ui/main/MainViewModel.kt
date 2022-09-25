package com.example.currencyexchange.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.currencyexchange.data.dataBaseRepository.ValutesDataBaseRepository
import com.example.currencyexchange.domain.model.ValuteItem
import com.example.currencyexchange.domain.model.usecases.GetValutesFromNetworkUseCase
import com.example.currencyexchange.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val TAG = "MainViewModel"

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

    private val _choiceBlockValuteState = MutableStateFlow(listOf<ValuteItem>())
    val choiceBlockValuteState get() = _choiceBlockValuteState.asStateFlow()

    private val _loadingState = MutableStateFlow(true)
    val loadingState get() = _loadingState.asStateFlow()

    private val _favouritesScreenState = MutableStateFlow(false)
    val favouritesScreenState get() = _favouritesScreenState.asStateFlow()

    private val _errorLoad = MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)
    val errorLoad get() = _errorLoad.asSharedFlow()

    private var startValutesToRub: List<ValuteItem>? = null

    init {
        emitPopularValutes()
    }

    private fun emitPopularValutes() {
        _loadingState.tryEmit(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val valutes = networkUseCase().toMutableList()
                valutes.add(0, ValuteItem("ru0000", "RUB", "Российский рубль", 1, 1.0, false))

                val idsFromDB = getFavouritesValutes().map { it.id }

                val valutesMap = valutes.map { valute ->
                    val isFavourite = idsFromDB.contains(valute.id)
                    valute.copy(isFavourite = isFavourite)
                }

                _valutesState.tryEmit(valutesMap)
                _choiceBlockValuteState.tryEmit(valutesMap)
                _loadingState.tryEmit(false)
                startValutesToRub = valutesMap
            } catch (e: Exception) {
                _errorLoad.tryEmit(true)
            }
        }
    }

    fun onClickSort(position: Int) {
        val valutes = _valutesState.value
        val sortComparator = createSortComparator(position)

        val sortedList = if (sortComparator != null)
            valutes.sortedWith(sortComparator)
        else
            valutes.shuffled()

        _valutesState.tryEmit(sortedList)
    }

    private fun createSortComparator(position: Int): Comparator<ValuteItem>? = when (position) {
        0 -> null
        1 -> Comparator { v1, v2 -> v1.name.compareTo(v2.name) }
        2 -> Comparator { v1, v2 -> v2.name.compareTo(v1.name) }
        3 -> Comparator { v1, v2 -> v1.value.compareTo(v2.value) }
        4 -> Comparator { v1, v2 -> v2.value.compareTo(v1.value) }
        else -> null
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
                Log.e(TAG, e.stackTrace.toString())
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
            Log.e(TAG, e.stackTrace.toString())
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
            Log.e(TAG, e.stackTrace.toString())
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

    fun onClickChoiceValute(value: Double) {
        if (btnFavouritesState.value || startValutesToRub == null) return
        val map = startValutesToRub!!.map { it.copy(value = it.value / value) }
        _valutesState.tryEmit(map)
    }
}