package io.aiico.currency.data

import java.math.BigDecimal

data class ExchangeRatesResponse(
    val baseCurrency: String,
    val rates: Map<String, BigDecimal>
)
