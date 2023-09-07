package com.example.currencyviewer.domain.usecase

import com.example.currencyviewer.data.model.Rate
import com.example.currencyviewer.data.model.RateResponse
import com.example.currencyviewer.domain.repository.RatesRepository
import javax.inject.Inject

//marking this as open due to issue with mocking final classes/methods in mockito
open class GetRatesListUseCase @Inject constructor(private val repository: RatesRepository) {

    sealed class ApiResult {
        data class Success(val rateList: List<Rate>): ApiResult()
        object Error: ApiResult()
        object Loading: ApiResult()

    }

    //marking this as open due to issue with mocking final classes/methods in mockito
    open suspend fun execute(): RateResponse {
        return repository.getRates()
    }

}