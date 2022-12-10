package com.glownia.maciej.exchangerates.api

import com.glownia.maciej.exchangerates.data.ExchangeRatesData
import retrofit2.http.*

interface ExchangeRatesDataApi {

    @GET("/fixer/{date}")
    @Headers("apikey: UJSUxXUxVns23fo5rZHlu31PM6PMubKb")
    suspend fun fetchExchangeRatesData(@Path("date") date: String): ExchangeRatesData
}
