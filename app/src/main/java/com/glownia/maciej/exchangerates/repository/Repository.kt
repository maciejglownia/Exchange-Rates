package com.glownia.maciej.exchangerates.repository

import com.glownia.maciej.exchangerates.api.RetrofitInstance

class Repository() {
    suspend fun getDataFromApi(date: String) =
        RetrofitInstance.api.fetchExchangeRatesData(date)
}