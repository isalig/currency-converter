package io.aiico.currency

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.aiico.currency.entity.CurrencyModel
import io.reactivex.Single
import org.junit.Test
import java.math.BigDecimal

class CurrencyInteractorTest {

    @Test
    fun `interactor returns list of currencies`() {
        // arrange
        val response = ExchangeRatesResponse("USD", mapOf("RUB" to BigDecimal.ONE))
        val api: CurrencyApi = mock {
            on(mock.getExchangeRates(any())).thenReturn(Single.just(response))
        }
        val currencies = listOf(
            CurrencyModel("USD", BigDecimal.ONE),
            CurrencyModel("RUB", BigDecimal.ONE)
        )
        val sup = CurrencyInteractor(api)

        // act
        val currenciesRequest = sup
            .getCurrencies("USD")
            .test()
            .await()

        // assert
        currenciesRequest.assertValue { value ->
            print(value)
            value == currencies
        }
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
            .getCurrencies("USD")
            .test()
            .await()

        //assert
        verify(api).getExchangeRates(any())
    }

    @Test
    fun `interactor returns base currency first`() {
        // arrange
        val response = ExchangeRatesResponse(
            "USD",
            mapOf(
                "RUB" to BigDecimal.ZERO,
                "USD" to BigDecimal.ZERO,
                "JPY" to BigDecimal.ZERO
            )
        )
        val api: CurrencyApi = mock {
            on(mock.getExchangeRates(eq("USD"))).thenReturn(Single.just(response))
        }
        val sup = CurrencyInteractor(api)

        // act
        val currenciesRequest = sup
            .getCurrencies("USD")
            .test()
            .await()

        // assert
        currenciesRequest.assertValue { currencies ->
            print("$currencies")
            currencies.first().code == "USD"
        }
    }
}