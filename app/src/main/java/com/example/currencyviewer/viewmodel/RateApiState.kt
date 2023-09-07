package com.example.currencyviewer.viewmodel

import com.example.currencyviewer.network.data.Rate

sealed class RateApiState {

    data class Success(val rateList: List<Rate>): RateApiState()
    object Error: RateApiState()

}