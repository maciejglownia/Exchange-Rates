package com.glownia.maciej.exchangerates.ui.viemodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glownia.maciej.exchangerates.data.ExchangeRatesData
import com.glownia.maciej.exchangerates.data.SingleRowDataPatternDto
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
    private val _exchangeRatesDataList = MutableLiveData<List<SingleRowDataPatternDto>>()
    val exchangeRatesDataList: LiveData<List<SingleRowDataPatternDto>>
        get() = _exchangeRatesDataList

    // Represents current date when user open application. Date is in format "YYYY-MM-DD".
    private var _requestedDate = LocalDate.now()

    // Represents response status. Will be used during presenting content to user.
    private var _exchangeRatesDataResponse: MutableLiveData<NetworkResult<ExchangeRatesData>> =
        MutableLiveData()
    val exchangeRatesDataResponse: LiveData<NetworkResult<ExchangeRatesData>>
        get() = _exchangeRatesDataResponse

    init {
        getExchangeRatesData()
    }

    fun getExchangeRatesData() = viewModelScope.launch {
        getExchangeRatesDataSafeCall()
    }

    private suspend fun getExchangeRatesDataSafeCall() {
        _exchangeRatesDataResponse.value = NetworkResult.Loading()
        delay(Constants.GETTING_EXCHANGES_RATES_DAYA_FROM_API_TIME_DELAY)
        try {
            val response = repository.getDataFromApi(_requestedDate.toString())
            _exchangeRatesDataResponse.value = handleExchangeRatesDataResponse(response)
            response.body()?.let {
                createListContainingExchangeRatesDataGetFromApi(it)
            }
            // After every time when the app gets data from API, the requestedDate is
            // changing to previous one until it will meet the oldest available date in API.
            _requestedDate = _requestedDate.minusDays(1)
        } catch (e: IOException) {
            _exchangeRatesDataResponse.value = NetworkResult.Error("Exchange rates data not found.")
        } catch (e: HttpException) {
            _exchangeRatesDataResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    // To this list a new object will be added -> list will be displaying in the ExchangeRateDataFragment
    private fun createListContainingExchangeRatesDataGetFromApi(result: ExchangeRatesData) {
        val exchangeRatesDataList = ArrayList<SingleRowDataPatternDto>()
        val formattedDate = formatDateToOneNeededToDisplayToUser(result.date).toString()
        exchangeRatesDataList.add(SingleRowDataPatternDto(DAY_WORD,
            "$formattedDate :",
            result.base,
            formattedDate))
        // Map iterating
        result.rates.forEach { (currencySymbol, valueAccordingToBaseCurrency) ->
            exchangeRatesDataList.add(SingleRowDataPatternDto("$currencySymbol :",
                valueAccordingToBaseCurrency.toString(), result.base, formattedDate))
        }
        _exchangeRatesDataList.value = exchangeRatesDataList
    }

    // Date needs to be formatted to display it to user in fragments in proper form.
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