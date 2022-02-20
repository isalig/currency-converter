package io.aiico.currency.data.repository

import io.aiico.currency.data.mapper.ExchangeRateMapper
import io.aiico.currency.data.source.ExchangeRatesApi
import io.aiico.currency.domain.model.CurrencyCode
import io.aiico.currency.domain.model.ExchangeRate
import io.aiico.currency.domain.repository.ExchangeRatesRepository
import javax.inject.Inject

class ExchangeRatesRepositoryImpl @Inject constructor(
    private val source: ExchangeRatesApi
) : ExchangeRatesRepository {

    override suspend fun getExchangeRates(baseCurrencyCode: CurrencyCode): List<ExchangeRate> =
        source.getExchangeRates(baseCurrencyCode.code)
            .rates
            .entries
            .map(ExchangeRateMapper::ExchangeRate)
}
