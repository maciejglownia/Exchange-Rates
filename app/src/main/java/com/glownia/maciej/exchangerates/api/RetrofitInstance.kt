package com.glownia.maciej.exchangerates.api

import com.glownia.maciej.exchangerates.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance {

    companion object {

//        var gson = GsonBuilder()
//            .setDateFormat("YYYY-MM-DD")
//            .create()

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // when using var gson -> .create(gson)
                .client(client)
                .build()
        }

        /**
         * API object will be used everywhere to network request
         */
        val api by lazy {
            retrofit.create(ExchangeRatesDataApi::class.java)
        }
    }
}