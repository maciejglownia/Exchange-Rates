package com.glownia.maciej.exchangerates.utils

/**
 * We are able to use this Resource class in Repository interface to wrap ExchangeRateResponse.
 * This way we can check in the ViewModel if the ExchangeRateResponse was successful or not.
 * Also it helps to detect if something went wrong.
 */
sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
    ) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)
    class Loading<T> : NetworkResult<T>()
}