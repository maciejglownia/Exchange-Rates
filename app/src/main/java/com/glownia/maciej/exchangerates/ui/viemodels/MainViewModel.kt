package com.glownia.maciej.exchangerates.ui.viemodels

import android.util.Log
import androidx.lifecycle.*
import com.glownia.maciej.exchangerates.data.ExchangeRateData
import com.glownia.maciej.exchangerates.data.Rates
import com.glownia.maciej.exchangerates.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel(state: SavedStateHandle) : ViewModel() {

    private val TAG = "MainScreenViewModel"
    val repository: Repository = Repository()


    private val _exRatesData = MutableLiveData<ExchangeRateData>()
    val exRatesData: LiveData<ExchangeRateData>
        get() = _exRatesData

    private val _rates = MutableLiveData<List<Rates>>()
    val rates: LiveData<List<Rates>>
        get() = _rates

    init {
        getExRates()
    }

    private fun getExRates() {
        viewModelScope.launch {
            try {
                Log.e("LoginViewModel", "getUserData() is trying...")
                val result = repository.getDataFromApi(CURRENT_DATE)
                _exRatesData.value = result

            } catch (e: IOException) {
                Log.d(TAG, "getExchangeRatesSafeCall: is IOException...")

            } catch (e: HttpException) {
                Log.d(TAG, "getExchangeRatesSafeCall: is HttpException...")
            }
        }
    }

    companion object {
        private const val CURRENT_DATE = "2022-12-07"
    }
}