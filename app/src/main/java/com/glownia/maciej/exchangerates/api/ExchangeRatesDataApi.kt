package com.glownia.maciej.exchangerates.api

import com.glownia.maciej.exchangerates.data.ExchangeRatesData
import retrofit2.http.*

interface ExchangeRatesDataApi {

    @GET("/fixer/{date}")
    @Headers("apikey: KDlIVBCbY2rmBeHJhK9xH4kSOnevn3u9")
    suspend fun fetchExchangeRatesData(@Path("date") date: String): ExchangeRatesData
}
