package com.example.currencyviewer.data.repository

import com.example.currencyviewer.data.api.RatesApiClient
import com.example.currencyviewer.data.model.RateResponse
import com.example.currencyviewer.domain.repository.RatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(private val apiClient: RatesApiClient): RatesRepository {

    override suspend fun getRates(): RateResponse {
        return withContext(Dispatchers.IO) {
            apiClient.getRates()
        }
    }

}