package com.example.currencyviewer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.currencyviewer.application.viewmodel.CurrencyListViewModel
import com.example.currencyviewer.data.model.Rate
import com.example.currencyviewer.data.model.RateResponse
import com.example.currencyviewer.domain.usecase.GetRatesListUseCase
import com.example.currencyviewer.domain.usecase.GetRatesListUseCase.ApiResult.Error
import com.example.currencyviewer.domain.usecase.GetRatesListUseCase.ApiResult.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrencyListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val dispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var useCase: GetRatesListUseCase

    private lateinit var viewModel: CurrencyListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `use case should return list of rates when api response is successful`() = runTest {
        val rate1 = Rate(id = "east-caribbean-dollar", symbol = "XCD", currencySymbol = "$", type = "fiat", rateUsd = "0.3700209061811993")
        val rate2 = Rate(id = "sri-lankan-rupee", symbol = "LKR", currencySymbol = "₨", type = "fiat", rateUsd = "0.0030981346896273")
        val response = RateResponse(listOf(rate1, rate2))
        whenever(useCase.execute()).thenReturn(response)
        viewModel = CurrencyListViewModel(useCase)
        assertTrue(viewModel.rateList.value is Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `use case should return an empty list when api response is unsuccessful`() = runTest {
        val response = RateResponse(emptyList())
        whenever(useCase.execute()).thenReturn(response)
        viewModel = CurrencyListViewModel(useCase)
        assertTrue(viewModel.rateList.value is Error)
    }
}