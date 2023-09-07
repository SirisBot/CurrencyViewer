package com.example.currencyviewer.domain.repository

import com.example.currencyviewer.data.model.RateResponse

interface RatesRepository {
    suspend fun getRates(): RateResponse

}