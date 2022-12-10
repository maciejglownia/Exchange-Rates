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

class MainViewModel : ViewModel() {

    private val repository: Repository = Repository()

    private val _exchangeRatesDataList = MutableLiveData<List<SingleRowDataPatternDto>>()
    val exchangeRatesDataList: LiveData<List<SingleRowDataPatternDto>>
        get() = _exchangeRatesDataList

    init {
        getExchangeRates()
    }

    private fun getExchangeRates() {
        viewModelScope.launch {
            try {
                Log.i(MAIN_VIEW_MODEL_TAG, "getExchangeRates() is trying to get data from API.")
                val result = repository.getDataFromApi(CURRENT_DATE)

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

    companion object {
        private const val CURRENT_DATE = "2022-12-07"
    }
}