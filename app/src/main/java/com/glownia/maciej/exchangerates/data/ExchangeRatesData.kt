package com.glownia.maciej.exchangerates.data

import com.google.gson.annotations.SerializedName

/**
 * JSON object which contains all values needed to display data in single rows.
 * @see [SingleRowDataPatternDto]
 */
data class ExchangeRatesData(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: Map<String, Double>,
)