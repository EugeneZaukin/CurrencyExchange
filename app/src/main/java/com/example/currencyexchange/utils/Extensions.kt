package com.example.currencyexchange.utils

import com.example.currencyexchange.data.model.*
import com.example.currencyexchange.domain.model.ValuteItem
import java.math.RoundingMode

fun Double.roundToTwoCharacters(): Double =
    this.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()

fun ValuteItem.toValuteDB(): ValuteDB =
    this.run {
        ValuteDB(id, charCode, name, value)
    }

fun ValuteDB.toFavouriteValuteItem(): ValuteItem =
    this.run {
        ValuteItem(idServer, charCode, name, value, true)
    }

fun Valute.toValuteItem(isFavourite: Boolean = false): ValuteItem =
    this.run {
        ValuteItem(id, charCode, name, value.roundToTwoCharacters(), isFavourite)
    }