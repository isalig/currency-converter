package io.aiico.currency.domain.usecase

import io.aiico.currency.domain.model.CurrencyCode
import io.aiico.currency.domain.model.ExchangeRate
import io.aiico.currency.domain.repository.ExchangeRatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(
    private val remoteRepository: ExchangeRatesRepository
) {

    suspend operator fun invoke(code: CurrencyCode): List<ExchangeRate> =
        withContext(Dispatchers.IO) {
            remoteRepository.getExchangeRates(code)
        }
}
