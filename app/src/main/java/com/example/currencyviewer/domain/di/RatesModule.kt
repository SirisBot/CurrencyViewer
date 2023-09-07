package com.example.currencyviewer.domain.di

import com.example.currencyviewer.data.api.RatesApiClient
import com.example.currencyviewer.data.repository.RatesRepositoryImpl
import com.example.currencyviewer.domain.usecase.GetRatesListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RatesModule {

    @Provides
    @ViewModelScoped
    fun providesUseCase(): GetRatesListUseCase {
        return GetRatesListUseCase(RatesRepositoryImpl(RatesApiClient()))
    }

}