package com.glownia.maciej.exchangerates.ui.viemodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glownia.maciej.exchangerates.data.ExchangeRatesData
import com.glownia.maciej.exchangerates.data.SingleRowDataPatternDto
import com.glownia.maciej.exchangerates.repository.Repository
import com.glownia.maciej.exchangerates.utils.Constants.DAY_WORD
import com.glownia.maciej.exchangerates.utils.Constants.MAIN_VIEW_MODEL_TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
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

    // Represents current date formatted to "DD-MM-YYYY"
    private var _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String>
        get() = _currentDate

    init {
        getExchangeRates()
    }

    fun getExchangeRates() {
        viewModelScope.launch {
            try {
                Log.i(MAIN_VIEW_MODEL_TAG,
                    "getExchangeRates() : requestDate now is: $_requestedDate.")
                // Get data
                val result = repository.getDataFromApi(_requestedDate.toString())
                Log.i(MAIN_VIEW_MODEL_TAG, "getExchangeRates() getDataFromApi executed.")
                // Create objects and add to list
                // data - base - rates as <symbol, value>
                createListContainingExchangeRatesDataGetFromApi(result)
                _currentDate.value = formatDateToOneNeededToDisplayToUser(_requestedDate.toString())

                // After every time when the app gets data from API, the requestedDate is
                // changing to previous one until it will meet the oldest available date in API.
                _requestedDate = _requestedDate.minusDays(1)
                Log.i(MAIN_VIEW_MODEL_TAG,
                    "getExchangeRates() : requestDate after subtract 1 is: $_requestedDate.")
            } catch (e: IOException) {
                Log.i(MAIN_VIEW_MODEL_TAG, "getExchangeRates(): ${e.message}")
            } catch (e: HttpException) {
                Log.i(MAIN_VIEW_MODEL_TAG, "getExchangeRates(): ${e.message}")
            }
        }
    }

    // To this list a new object will be added -> list will be displaying in in the ExchangeRateDataFragment
    private fun createListContainingExchangeRatesDataGetFromApi(result: ExchangeRatesData) {
        val exchangeRatesDataList = ArrayList<SingleRowDataPatternDto>()
        val formattedDate = formatDateToOneNeededToDisplayToUser(result.date)
        exchangeRatesDataList.add(SingleRowDataPatternDto(DAY_WORD, "$formattedDate :"))
        result.rates.forEach { (currencySymbol, valueAccordingToBaseCurrency) ->
            exchangeRatesDataList.add(SingleRowDataPatternDto("$currencySymbol :",
                valueAccordingToBaseCurrency.toString()))
        }
        _exchangeRatesDataList.value = exchangeRatesDataList
    }

    private fun formatDateToOneNeededToDisplayToUser(dateToFormat: String): String? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return LocalDate.parse(dateToFormat, formatter).format(formatter2)
    }
}