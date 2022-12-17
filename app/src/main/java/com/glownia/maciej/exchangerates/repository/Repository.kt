package com.glownia.maciej.exchangerates.repository

import com.glownia.maciej.exchangerates.api.RetrofitInstance
import com.glownia.maciej.exchangerates.models.Rates
import retrofit2.Response

class Repository() {
    suspend fun getRates(date: String) : Response<Rates> =
        RetrofitInstance.api.fetchRates(date)
}