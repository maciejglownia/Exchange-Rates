package com.glownia.maciej.exchangerates.ui.viemodels

import android.util.Log
import androidx.lifecycle.*
import com.glownia.maciej.exchangerates.data.ExchangeRatesData
import com.glownia.maciej.exchangerates.data.SingleRowDataPatternDto
import com.glownia.maciej.exchangerates.repository.Repository
import com.glownia.maciej.exchangerates.utils.Constants
import com.glownia.maciej.exchangerates.utils.Constants.DAY_WORD
import com.glownia.maciej.exchangerates.utils.Constants.MAIN_VIEW_MODEL_TAG
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

    private val _exchangeRatesDataList = MutableLiveData<List<SingleRowDataPatternDto>>()
    val exchangeRatesDataList: LiveData<List<SingleRowDataPatternDto>>
        get() = _exchangeRatesDataList

    // Represents current date when user open application. Date is in format "YYYY-MM-DD".
    private var _requestedDate = LocalDate.now()

    var exchangeRatesDataResponse: MutableLiveData<NetworkResult<ExchangeRatesData>> = MutableLiveData()

    init {
        getExchangeRates()
    }

    fun getExchangeRates() {
        viewModelScope.launch {
            try {
                Log.i(MAIN_VIEW_MODEL_TAG,
                    "getExchangeRates() : requestDate now is: $_requestedDate.")
                delay(Constants.GETTING_EXCHANGES_RATES_DAYA_FROM_API_TIME_DELAY)
                exchangeRatesDataResponse.value = NetworkResult.Loading()
                val response = repository.getDataFromApi(_requestedDate.toString())
                exchangeRatesDataResponse.value = handleExchangeRatesDataResponse(response)
                // Create objects and add to list
                // data - base - rates as <symbol, value>
                response.body()?.let { createListContainingExchangeRatesDataGetFromApi(it) }
                // After every time when the app gets data from API, the requestedDate is
                // changing to previous one until it will meet the oldest available date in API.
                _requestedDate = _requestedDate.minusDays(1)
            } catch (e: IOException) {
                exchangeRatesDataResponse.value = NetworkResult.Error("Exchange rates data not found.")
            } catch (e: HttpException) {
                exchangeRatesDataResponse.value = NetworkResult.Error("No Internet Connection.")
            }
        }
    }

    // To this list a new object will be added -> list will be displaying in in the ExchangeRateDataFragment
    private fun createListContainingExchangeRatesDataGetFromApi(result: ExchangeRatesData) {
        val exchangeRatesDataList = ArrayList<SingleRowDataPatternDto>()
        val formattedDate = formatDateToOneNeededToDisplayToUser(result.date).toString()
        exchangeRatesDataList.add(SingleRowDataPatternDto(DAY_WORD, "$formattedDate :", result.base, formattedDate))
        // Map iterating
        result.rates.forEach { (currencySymbol, valueAccordingToBaseCurrency) ->
            exchangeRatesDataList.add(SingleRowDataPatternDto("$currencySymbol :",
                valueAccordingToBaseCurrency.toString(), result.base, formattedDate))
        }
        _exchangeRatesDataList.value = exchangeRatesDataList
    }

    private fun formatDateToOneNeededToDisplayToUser(dateToFormat: String): String? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return LocalDate.parse(dateToFormat, formatter).format(formatter2)
    }

    private fun handleExchangeRatesDataResponse(response: Response<ExchangeRatesData>): NetworkResult<ExchangeRatesData> {
        when {
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
            response.isSuccessful -> {
                val exchangeRatesData = response.body()
                return NetworkResult.Success(exchangeRatesData!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }
}