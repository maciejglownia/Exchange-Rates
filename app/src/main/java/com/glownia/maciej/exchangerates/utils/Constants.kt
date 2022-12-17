package com.glownia.maciej.exchangerates.utils


object Constants {

    // const val BASE_URL = "https://api.apilayer.com/"

    // Communication
    /**
     * @param BASE_URL
     * To obtain this URL from your local machine go to start -> cmd -> ipconfig -> Ipv4
     * You need it to communicate with your Android application instead of e.g. localhost:8080
     */
    const val BASE_URL = "http://192.168.0.17:8080/" // when http -> android:usesCleartextTraffic="true"

    const val CLIENT_ID = "w1Gx33qyp9mNVFbWV5owOQkcUVuBd2an"

    // Names to display
    const val DAY_WORD = "Day"

    // TAGs
    const val RATES_FRAGMENT = "RatesFragment"

    // Delays
    const val GETTING_RATES_FROM_API_TIME_DELAY = 200L
}