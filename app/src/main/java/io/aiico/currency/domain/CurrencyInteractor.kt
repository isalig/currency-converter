package io.aiico.currency.domain

import io.aiico.currency.data.CurrencyApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.math.BigDecimal
import javax.inject.Inject

class CurrencyInteractor @Inject constructor(private val remoteRepository: CurrencyApi) {

    private val baseCurrencyChangePublisher = MutableStateFlow<String?>(null)
    private val baseCurrencyChangeFlow: Flow<String> =
        baseCurrencyChangePublisher.asStateFlow().filterNotNull()

    suspend fun onBaseCurrencyChange(currencyCode: String) {
        baseCurrencyChangePublisher.emit(currencyCode)
    }

    fun getCurrenciesChange(): Flow<Map<String, BigDecimal>> =
        baseCurrencyChangeFlow
            .distinctUntilChanged()
            .flatMapLatest(::loadCurrenciesInterval)

    private fun loadCurrenciesInterval(code: String): Flow<Map<String, BigDecimal>> = flow {
        while (true) {
            emit(remoteRepository.getExchangeRates(code).rates)
            delay(1_000)
        }
    }.cancellable()

}
