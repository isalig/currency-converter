package io.aiico.currency.ui.mapper

import io.aiico.currency.domain.model.ExchangeRate
import io.aiico.currency.ui.model.CurrencyModel

object CurrencyModelMapper {

    fun CurrencyModel(exchangeRate: ExchangeRate): CurrencyModel = CurrencyModel(
        exchangeRate.currency.code,
        exchangeRate.rate.toString()
    )
}
