package io.aiico.currency.entity

import java.math.BigDecimal

data class CurrencyModel(
    val code: String,
    val rate: BigDecimal
)