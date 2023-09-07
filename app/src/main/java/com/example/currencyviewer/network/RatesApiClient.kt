package com.example.currencyviewer.network

import com.example.currencyviewer.network.data.RateResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class RatesApiClient {

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
        return withContext(Dispatchers.IO) {
            httpClient.get(url.build())
        }
    }

}