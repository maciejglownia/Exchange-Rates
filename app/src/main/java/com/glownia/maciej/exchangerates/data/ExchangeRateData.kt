package com.glownia.maciej.exchangerates.data


import com.google.gson.annotations.SerializedName

data class ExchangeRateData(
    @SerializedName("base")
    val base: String, // e.g. GBP
    @SerializedName("date")
    val date: String, // e.g. 2013-12-24
    @SerializedName("rates")
    val rates: Rates,
)