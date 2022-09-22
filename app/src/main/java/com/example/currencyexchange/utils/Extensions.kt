package com.example.currencyexchange.utils

import com.example.currencyexchange.data.model.*
import com.example.currencyexchange.domain.model.ValuteItem
import java.math.RoundingMode

fun Double.roundToTwoCharacters(): Double =
    this.toBigDecimal().setScale(3, RoundingMode.HALF_UP).toDouble()

fun ValuteItem.toValuteDB(): ValuteDB =
    this.run {
        ValuteDB(id, charCode, name, nominal, value)
    }

fun ValuteDB.toFavouriteValuteItem(): ValuteItem =
    this.run {
        ValuteItem(idServer, charCode, name, nominal, value, true)
    }

fun Valute.toValuteItem(isFavourite: Boolean = false): ValuteItem =
    this.run {
        ValuteItem(id, charCode, name, nominal, 1 / (value/nominal), isFavourite)
    }