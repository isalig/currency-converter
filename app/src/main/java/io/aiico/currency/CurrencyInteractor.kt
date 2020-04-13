package io.aiico.currency

import io.aiico.currency.entity.CurrencyModel
import io.reactivex.Single
import java.math.BigDecimal
import javax.inject.Inject

class CurrencyInteractor @Inject constructor(private val remoteRepository: CurrencyApi) {

    fun getCurrencies(code: String): Single<List<CurrencyModel>> =
        remoteRepository
            .getExchangeRates(code)
            .toObservable()
            .concatMapIterable { response ->
                response.rates.toList()
            }
            .map { pair ->
                CurrencyModel(
                    code = pair.first,
                    rate = pair.second
                )
            }
            .collect(
                { mutableListOf(CurrencyModel(code, BigDecimal.ONE)) },
                { list, value ->
                    list.add(value)
                }
            )
            .map { it }
}