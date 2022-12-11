package com.glownia.maciej.exchangerates.api

import com.glownia.maciej.exchangerates.data.ExchangeRatesData
import com.glownia.maciej.exchangerates.utils.Constants.CLIENT_ID
import retrofit2.Response
import retrofit2.http.*

interface ExchangeRatesDataApi {

    @GET("/fixer/{date}")
    @Headers("apikey: $CLIENT_ID")
//    @Headers("apikey: UJKb") // this line is to test responses
    suspend fun fetchExchangeRatesData(@Path("date") date: String): Response<ExchangeRatesData>
}
