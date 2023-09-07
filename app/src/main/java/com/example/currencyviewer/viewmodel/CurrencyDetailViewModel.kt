package com.example.currencyviewer.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyDetailViewModel @Inject constructor(
    state: SavedStateHandle
) : ViewModel() {
    val rateId = state.get<String>("id") ?: ""
    val rateSymbol = state.get<String>("symbol") ?: ""
    val rateCurrencySymbol = state.get<String>("currencySymbol") ?: ""
    val rateUsd = state.get<String>("rateUsd") ?: ""
    val rateType = state.get<String>("type") ?: ""
}