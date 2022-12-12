package com.glownia.maciej.exchangerates.utils

import com.glownia.maciej.exchangerates.BuildConfig

object Constants {

    // Communication
    const val BASE_URL = "https://api.apilayer.com/"
    // This will be automatically generated and contain API key which has been added in gradle.properties
     const val CLIENT_ID = BuildConfig.FIXER_ACCESS_KEY

    // Names to display
    const val DAY_WORD = "Dzień"

    // TAGs
    const val EXCHANGE_RATES_FRAGMENT_TAG = "ExchangeRatesFragment"

    // Delays
    const val GETTING_EXCHANGES_RATES_DAYA_FROM_API_TIME_DELAY = 200L
}