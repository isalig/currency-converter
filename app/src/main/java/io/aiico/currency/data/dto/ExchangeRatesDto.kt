package io.aiico.currency.data.dto

import java.math.BigDecimal

data class ExchangeRatesDto(
    val base: String,
    val rates: Map<String, BigDecimal>
)
