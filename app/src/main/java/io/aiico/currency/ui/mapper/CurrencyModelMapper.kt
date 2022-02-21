package io.aiico.currency.ui.mapper

import io.aiico.currency.domain.model.ExchangeRate
import io.aiico.currency.ui.model.CurrencyModel
import java.math.RoundingMode

object CurrencyModelMapper {

    private const val SCALE = 2

    fun CurrencyModel(exchangeRate: ExchangeRate): CurrencyModel = CurrencyModel(
        exchangeRate.currency.code,
        exchangeRate.rate.setScale(SCALE, RoundingMode.HALF_EVEN).stripTrailingZeros()
            .toPlainString()
    )
}
