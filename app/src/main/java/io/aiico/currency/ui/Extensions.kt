package io.aiico.currency.ui

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.asFormattedString(scale: Int): String =
    setScale(scale, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString()
