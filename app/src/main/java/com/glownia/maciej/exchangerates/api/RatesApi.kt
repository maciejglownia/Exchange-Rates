package com.glownia.maciej.exchangerates.api

import com.glownia.maciej.exchangerates.models.Rates
import retrofit2.Response
import retrofit2.http.*

interface RatesApi {


//    @GET("/fixer/{date}")
//    @Headers("apikey: $CLIENT_ID")
    /**
     * Gets data from a server on local machine: ktor-exchange-rates
     */
    @GET("/{date}")
    suspend fun fetchRates(@Path("date") date: String): Response<Rates>
}
