package com.example.currencyviewer

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.currencyviewer.network.RatesApiClient
import com.example.currencyviewer.network.data.Rate
import com.example.currencyviewer.network.data.RateResponse
import com.example.currencyviewer.viewmodel.CurrencyListViewModel
import com.example.currencyviewer.viewmodel.RateApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class CurrencyListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiClient: RatesApiClient

    private lateinit var viewModel: CurrencyListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        viewModel = CurrencyListViewModel(apiClient)
        Dispatchers.setMain(Dispatchers.Main)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `rate service should return list of rates when api response is successful`() = runTest {
        val rate1 = Rate(id = "east-caribbean-dollar", symbol = "XCD", currencySymbol = "$", type = "fiat", rateUsd = "0.3700209061811993")
        val rate2 = Rate(id = "sri-lankan-rupee", symbol = "LKR", currencySymbol = "₨", type = "fiat", rateUsd = "0.0030981346896273")
        val response = RateResponse(listOf(rate1, rate2))
        whenever(apiClient.getRates()).thenReturn(response)
        viewModel.getRates()
        assertTrue(viewModel.rateList.value is RateApiState.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `rate service should return an empty list when api response is unsuccessful`() = runTest {
        val response = RateResponse(emptyList())
        whenever(apiClient.getRates()).thenReturn(response)
        viewModel.getRates()
        assertTrue(viewModel.rateList.value is RateApiState.Error)
    }
}