package com.example.currencyviewer.application.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyviewer.domain.usecase.GetRatesListUseCase
import com.example.currencyviewer.domain.usecase.GetRatesListUseCase.ApiResult
import com.example.currencyviewer.domain.usecase.GetRatesListUseCase.ApiResult.Error
import com.example.currencyviewer.domain.usecase.GetRatesListUseCase.ApiResult.Loading
import com.example.currencyviewer.domain.usecase.GetRatesListUseCase.ApiResult.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(private val getRatesListUseCase: GetRatesListUseCase) : ViewModel() {

    private var _rateList: MutableLiveData<ApiResult> = MutableLiveData(Loading)
    val rateList: LiveData<ApiResult> = _rateList

    init {
        getRates()
    }

    @VisibleForTesting
    fun getRates() {
        viewModelScope.launch {
            val response = getRatesListUseCase.execute()
            _rateList.value = if (response.data.isEmpty()) {
                Error
            } else {
                Success(response.data)
            }
        }
    }



}