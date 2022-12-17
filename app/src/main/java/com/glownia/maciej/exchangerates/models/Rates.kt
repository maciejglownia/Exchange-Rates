package com.glownia.maciej.exchangerates.models

import com.google.gson.annotations.SerializedName

/**
 * JSON object which contains all values needed to display data in single rows.
 * @see [RatesDto]
 */
data class Rates(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: Map<String, Double>,
)