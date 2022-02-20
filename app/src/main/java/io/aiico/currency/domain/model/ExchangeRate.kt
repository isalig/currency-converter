package io.aiico.currency.domain.model

import java.math.BigDecimal

data class ExchangeRate(
    val currency: CurrencyCode,
    val rate: BigDecimal
)
