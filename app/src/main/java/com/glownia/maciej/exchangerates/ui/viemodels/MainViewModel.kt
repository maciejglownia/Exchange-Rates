package com.glownia.maciej.exchangerates.ui.viemodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glownia.maciej.exchangerates.models.Rates
import com.glownia.maciej.exchangerates.data.RatesDto
import com.glownia.maciej.exchangerates.repository.Repository
import com.glownia.maciej.exchangerates.utils.Constants
import com.glownia.maciej.exchangerates.utils.Constants.DAY_WORD
import com.glownia.maciej.exchangerates.utils.NetworkResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainViewModel : ViewModel() {

    private val repository: Repository = Repository()

    // Represents list contains  date and exchange rates for this day.
    private val _ratesList = MutableLiveData<ArrayList<RatesDto>>()
    val ratesList: LiveData<ArrayList<RatesDto>>
        get() = _ratesList

    // Represents current date when user open application. Date is in format "YYYY-MM-DD".
    private var requestedDate = LocalDate.now()

    // Represents response status. Will be used during presenting content to user.
    private var _ratesResponse: MutableLiveData<NetworkResult<Rates>> =
        MutableLiveData()
    val ratesResponse: LiveData<NetworkResult<Rates>>
        get() = _ratesResponse

    private val tempList = ArrayList<RatesDto>()

    init {
        getRates()
    }

    fun getRates() = viewModelScope.launch {
        getRatesSafeCall()
    }

    private suspend fun getRatesSafeCall() {
        _ratesResponse.value = NetworkResult.Loading()
        delay(Constants.GETTING_RATES_FROM_API_TIME_DELAY)
        try {
            val response = repository.getRates(requestedDate.toString())
            _ratesResponse.value = handleRatesResponse(response)
            response.body()?.let {
                createListContainingRates(it)
            }
            requestedDate = requestedDate.minusDays(1)
        } catch (e: IOException) {
            _ratesResponse.value = NetworkResult.Error("Exchange rates data not found.")
        } catch (e: HttpException) {
            _ratesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    // To this list a new object will be added -> list will be displaying in the ExchangeRateDataFragment
    private fun createListContainingRates(result: Rates) {
        val formattedDate = formatDateToOneNeededToDisplayToUser(result.date).toString()
        tempList.add(
            RatesDto(
                date = formattedDate,
            )
        )
        result.rates.forEach { (currencySymbol, valueAccordingToBaseCurrency) ->
            tempList.add(
                RatesDto(
                    name = currencySymbol,
                    date = formattedDate,
                    value = valueAccordingToBaseCurrency.toString(),
                )
            )
        }
        _ratesList.value = tempList
    }

    // Date needs to be formatted to display it to user in fragments in proper form.
    private fun formatDateToOneNeededToDisplayToUser(dateToFormat: String): String? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return LocalDate.parse(dateToFormat, formatter).format(formatter2)
    }

    private fun handleRatesResponse(response: Response<Rates>): NetworkResult<Rates> {
        when {
            response.isSuccessful -> {
                val exchangeRatesData = response.body()
                return NetworkResult.Success(exchangeRatesData!!)
            }
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout.")
            }
            response.code() == 400 -> {
                return NetworkResult.Error("Request unacceptable.")
            }
            response.code() == 401 -> {
                return NetworkResult.Error("No valid API key provided.")
            }
            response.code() == 404 -> {
                return NetworkResult.Error("The requested resource doesn't exist.")
            }
            response.code() == 429 -> {
                return NetworkResult.Error("API request limit exceeded.")
            }
            response.body()!!.date.isEmpty() || response.body()!!.base.isEmpty() ||
                    response.body()!!.rates.isEmpty() -> {
                return NetworkResult.Error("Exchange rates data not found.")
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }
}
