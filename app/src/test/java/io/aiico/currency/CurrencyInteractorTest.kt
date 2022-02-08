package io.aiico.currency

import org.junit.Test

class CurrencyInteractorTest {

    @Test
    fun `interactor returns list of currencies`() {
        // arrange
//        val rates = mapOf("RUB" to BigDecimal.ONE, "EUR" to BigDecimal("10"))
//        val response = ExchangeRatesResponse("USD", rates)
//        val api: CurrencyApi = mock {
//            on(mock.getExchangeRates(any())).thenReturn(Single.just(response))
//        }
//        val sup = CurrencyInteractor(api)
//
//        // act
//        val currenciesRequest = sup
//            .getCurrenciesChange()
//            .test()
//            .await()
//        sup.onBaseCurrencyChange("USD")
//
//        // assert
//        currenciesRequest.assertValue { value -> value == rates }
    }

    @Test
    fun `interactor calls api method`() {
        // arrange
//        val response = ExchangeRatesResponse("", mapOf("" to BigDecimal.ZERO))
//        val api: CurrencyApi = mock {
//            on(mock.getExchangeRates(any())).thenReturn(Single.just(response))
//        }
//        val sup = CurrencyInteractor(api)
//
//        // act
//        sup
//            .getCurrenciesChange()
//            .test()
//            .await()
//        sup.onBaseCurrencyChange("USD")
//
//        //assert
//        verify(api).getExchangeRates(any())
    }
}
