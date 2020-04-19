package io.aiico.currency

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.aiico.currency.data.CurrencyApi
import io.aiico.currency.data.ExchangeRatesResponse
import io.aiico.currency.domain.CurrencyInteractor
import io.reactivex.Single
import org.junit.Test
import java.math.BigDecimal

class CurrencyInteractorTest {

    @Test
    fun `interactor returns list of currencies`() {
        // arrange
        val rates = mapOf("RUB" to BigDecimal.ONE, "EUR" to BigDecimal("10"))
        val response = ExchangeRatesResponse("USD", rates)
        val api: CurrencyApi = mock {
            on(mock.getExchangeRates(any())).thenReturn(Single.just(response))
        }
        val sup = CurrencyInteractor(api)

        // act
        val currenciesRequest = sup
            .getCurrenciesChange()
            .test()
            .await()
        sup.onBaseCurrencyChange("USD")

        // assert
        currenciesRequest.assertValue { value -> value == rates }
    }

    @Test
    fun `interactor calls api method`() {
        // arrange
        val response = ExchangeRatesResponse("", mapOf("" to BigDecimal.ZERO))
        val api: CurrencyApi = mock {
            on(mock.getExchangeRates(any())).thenReturn(Single.just(response))
        }
        val sup = CurrencyInteractor(api)

        // act
        sup
            .getCurrenciesChange()
            .test()
            .await()
        sup.onBaseCurrencyChange("USD")

        //assert
        verify(api).getExchangeRates(any())
    }
}
