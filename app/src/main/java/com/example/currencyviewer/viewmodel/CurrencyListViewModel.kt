package com.example.currencyviewer.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.currencyviewer.domain.RateApiState
import com.example.currencyviewer.network.RatesApiClient
import com.example.currencyviewer.domain.RateApiState.Error
import com.example.currencyviewer.domain.RateApiState.Loading
import com.example.currencyviewer.domain.RateApiState.Success
import kotlinx.coroutines.launch

class CurrencyListViewModel(private val apiClient: RatesApiClient) : ViewModel() {

    private var _rateList: MutableLiveData<RateApiState> = MutableLiveData(Loading)
    val rateList: LiveData<RateApiState> = _rateList

    init {
        getRates()
    }

    @VisibleForTesting
    fun getRates() {
        viewModelScope.launch {
            val response = apiClient.getRates()
            _rateList.value = if (response.data.isEmpty()) {
                Error
            } else {
                Success(response.data)
            }
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return CurrencyListViewModel(
                    RatesApiClient()
                ) as T
            }
        }

    }

}