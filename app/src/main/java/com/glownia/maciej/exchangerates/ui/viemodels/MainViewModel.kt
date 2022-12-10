package com.glownia.maciej.exchangerates.ui.viemodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glownia.maciej.exchangerates.data.SingleRowDataPatternDto
import com.glownia.maciej.exchangerates.repository.Repository
import com.glownia.maciej.exchangerates.utils.Constants.DAY_WORD
import com.glownia.maciej.exchangerates.utils.Constants.MAIN_VIEW_MODEL_TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate

class MainViewModel : ViewModel() {

    private val repository: Repository = Repository()

    private val _exchangeRatesDataList = MutableLiveData<List<SingleRowDataPatternDto>>()
    val exchangeRatesDataList: LiveData<List<SingleRowDataPatternDto>>
        get() = _exchangeRatesDataList

    // Represents current date when user open application. Date is in format "YYYY-MM-DD".
    private var requestedDate = LocalDate.now() // 10 -> get -> 9

    init {
        getExchangeRates()
    }

    fun getExchangeRates() {
        viewModelScope.launch {
            try {
                Log.i(MAIN_VIEW_MODEL_TAG,
                    "getExchangeRates() : requestDate now is: $requestedDate.")
                val result = repository.getDataFromApi(requestedDate.toString())
                Log.i(MAIN_VIEW_MODEL_TAG, "getExchangeRates() getDataFromApi executed.")
                // After every time when the app gets data from API, the requestedDate is
                // changing to previous one until it will meet the oldest available date in API.
                requestedDate = requestedDate.minusDays(1)
                Log.i(MAIN_VIEW_MODEL_TAG,
                    "getExchangeRates() : requestDate after subtract 1 is: $requestedDate.")

                // To this list a new object will be added -> list will be displayed in fragment
                val rateDataList = ArrayList<SingleRowDataPatternDto>()
                rateDataList.add(SingleRowDataPatternDto(DAY_WORD, "${result.date} :"))
                result.rates.forEach { (currencySymbol, valueAccordingToBaseCurrency) ->
                    rateDataList.add(SingleRowDataPatternDto("$currencySymbol :",
                        valueAccordingToBaseCurrency.toString()))
                }
                _exchangeRatesDataList.value = rateDataList
            } catch (e: IOException) {
                Log.i(MAIN_VIEW_MODEL_TAG, "getExchangeRates(): ${e.message}")
            } catch (e: HttpException) {
                Log.i(MAIN_VIEW_MODEL_TAG, "getExchangeRates(): ${e.message}")
            }
        }
    }

    fun getCosTam() {
        TODO("Not yet implemented")
    }
}