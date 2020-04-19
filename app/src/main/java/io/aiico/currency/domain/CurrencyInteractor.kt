package io.aiico.currency.domain

import io.aiico.currency.data.CurrencyApi
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyInteractor @Inject constructor(private val remoteRepository: CurrencyApi) {

    private val baseCurrencyChangePublisher = PublishSubject.create<String>()

    fun onBaseCurrencyChange(currencyCode: String) {
        baseCurrencyChangePublisher.onNext(currencyCode)
    }

    fun getCurrenciesChange(): Observable<Map<String, BigDecimal>> =
        baseCurrencyChangePublisher
            .distinctUntilChanged()
            .switchMap { baseCurrencyCode -> loadCurrenciesInterval(baseCurrencyCode) }

    private fun loadCurrenciesInterval(code: String): Observable<Map<String, BigDecimal>> =
        Observable
            .interval(0, 1, TimeUnit.SECONDS)
            .concatMapSingle {
                remoteRepository
                    .getExchangeRates(code)
            }
            .map { response -> response.rates }
}