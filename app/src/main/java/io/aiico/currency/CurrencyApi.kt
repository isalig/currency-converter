package io.aiico.currency

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    fun getExchangeRates(@Query("base") baseCurrency: String): Single<ExchangeRatesResponse>
}
