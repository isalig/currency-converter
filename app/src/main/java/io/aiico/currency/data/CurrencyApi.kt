package io.aiico.currency.data

import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    suspend fun getExchangeRates(@Query("base") baseCurrency: String): ExchangeRatesResponse
}
