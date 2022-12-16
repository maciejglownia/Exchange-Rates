package com.glownia.maciej.exchangerates.api

import com.glownia.maciej.exchangerates.data.ExchangeRatesData
import com.glownia.maciej.exchangerates.utils.Constants.CLIENT_ID
import retrofit2.Response
import retrofit2.http.*

interface ExchangeRatesDataApi {


//    @GET("/fixer/{date}")
//    @Headers("apikey: $CLIENT_ID")
    /**
     * Gets data from a server on local machine: ktor-exchange-rates
     */
    @GET("/{date}")
    suspend fun fetchExchangeRatesData(@Path("date") date: String): Response<ExchangeRatesData>
}
