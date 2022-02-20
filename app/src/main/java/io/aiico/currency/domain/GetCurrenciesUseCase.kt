package io.aiico.currency.domain

import io.aiico.currency.data.CurrencyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val remoteRepository: CurrencyApi
) {

    suspend operator fun invoke(code: String): Map<String, BigDecimal> =
        withContext(Dispatchers.IO) {
            remoteRepository.getExchangeRates(code).rates
        }
}
