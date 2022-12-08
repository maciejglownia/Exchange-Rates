package com.glownia.maciej.exchangerates.data

import com.google.gson.annotations.SerializedName

data class Rates(
    @SerializedName("AED")
    val aED: Double,
    @SerializedName("AFN")
    val aFN: Double,
    @SerializedName("ALL")
    val aLL: Double
)