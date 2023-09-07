package com.example.currencyviewer.di

import com.example.currencyviewer.network.RatesApiClient
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
    fun providesApiClient(): RatesApiClient {
        return RatesApiClient()
    }

}