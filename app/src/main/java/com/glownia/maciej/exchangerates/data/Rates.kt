package com.glownia.maciej.exchangerates.data

import com.google.gson.annotations.SerializedName

data class Rates(
    @SerializedName("CAD")
    val cAD: Double,
    @SerializedName("EUR")
    val eUR: Double,
    @SerializedName("USD")
    val uSD: Double
)