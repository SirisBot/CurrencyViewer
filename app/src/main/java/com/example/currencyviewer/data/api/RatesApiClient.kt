package com.example.currencyviewer.data.api

import com.example.currencyviewer.data.model.RateResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import javax.inject.Inject

class RatesApiClient @Inject constructor() {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    suspend fun getRates(): RateResponse {
        val url = URLBuilder().apply {
            takeFrom("https://api.coincap.io/v2/rates")
        }
        return httpClient.get(url.build())
    }

}