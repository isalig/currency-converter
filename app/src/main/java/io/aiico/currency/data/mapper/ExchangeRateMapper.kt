package io.aiico.currency.data.mapper

import io.aiico.currency.domain.model.CurrencyCode
import io.aiico.currency.domain.model.ExchangeRate
import java.math.BigDecimal

object ExchangeRateMapper {

    fun ExchangeRate(entry: Map.Entry<String, BigDecimal>): ExchangeRate = with(entry) {
        ExchangeRate(currency = CurrencyCode(key), rate = value)
    }
}
