package com.example.currencyexchange.utils

import java.math.RoundingMode

fun Double.roundToTwoCharacters(): Double =
    this.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()