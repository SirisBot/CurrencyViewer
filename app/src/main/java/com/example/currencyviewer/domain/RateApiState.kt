package com.example.currencyviewer.domain

import com.example.currencyviewer.network.data.Rate

sealed class RateApiState {

    data class Success(val rateList: List<Rate>): RateApiState()
    object Error: RateApiState()
    object Loading: RateApiState()

}