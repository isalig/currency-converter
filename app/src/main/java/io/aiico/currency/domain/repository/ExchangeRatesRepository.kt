package io.aiico.currency.domain.repository

import io.aiico.currency.domain.model.CurrencyCode
import io.aiico.currency.domain.model.ExchangeRate

interface ExchangeRatesRepository {

    suspend fun getExchangeRates(baseCurrencyCode: CurrencyCode): List<ExchangeRate>
}
