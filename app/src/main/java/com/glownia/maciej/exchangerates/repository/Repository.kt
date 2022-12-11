package com.glownia.maciej.exchangerates.repository

import com.glownia.maciej.exchangerates.api.RetrofitInstance
import com.glownia.maciej.exchangerates.data.ExchangeRatesData
import retrofit2.Response

class Repository() {
    suspend fun getDataFromApi(date: String) : Response<ExchangeRatesData> =
        RetrofitInstance.api.fetchExchangeRatesData(date)
}