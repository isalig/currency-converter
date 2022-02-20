package io.aiico.currency.data.source

import io.aiico.currency.data.dto.ExchangeRatesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi {

    @GET("latest")
    suspend fun getExchangeRates(@Query("base") baseCurrency: String): ExchangeRatesDto
}
