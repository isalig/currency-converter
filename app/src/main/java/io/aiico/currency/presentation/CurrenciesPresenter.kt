package io.aiico.currency.presentation

import android.util.Log
import io.aiico.currency.BuildConfig
import io.aiico.currency.domain.CurrencyInteractor
import io.aiico.currency.presentation.entity.CurrencyViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

private const val SCALE = 2

@InjectViewState
class CurrenciesPresenter @Inject constructor(
    private val currencyInteractor: CurrencyInteractor
) : MvpPresenter<CurrenciesView>() {

    private var baseCurrencyCode = BuildConfig.DEFAULT_CURRENCY
    private var baseCurrencyAmount = BigDecimal(BuildConfig.DEFAULT_AMOUNT)
    private var currencies = ArrayList<CurrencyViewModel>()
    private var rates: Map<String, BigDecimal>? = null

    override fun onFirstViewAttach() {
        presenterScope.launch {
            currencyInteractor
                .getCurrenciesChange()
                .onEach(::onUpdateRates)
                .catch { error -> Log.e("Currency App", error.message, error) }

            currencyInteractor.onBaseCurrencyChange(baseCurrencyCode)
        }
    }

    private fun onUpdateRates(newRates: Map<String, BigDecimal>) {
        rates = newRates
        if (currencies.isEmpty()) {
            initCurrencies()
        } else {
            updateCurrencies(null)
        }

        viewState.showCurrencies(currencies)
    }

    private fun initCurrencies() {
        currencies.add(fromAmount(baseCurrencyCode, baseCurrencyAmount))
        rates?.forEach { entry ->
            currencies.add(fromAmount(entry.key, entry.value.multiply(baseCurrencyAmount)))
        }
    }

    private fun fromAmount(code: String, amount: BigDecimal) = CurrencyViewModel(
        code,
        amount.asFormattedString(SCALE)
    )

    private fun updateCurrencies(ignoredCurrencyCode: String?) {
        currencies.forEach { currency ->
            val newRate = rates?.get(currency.code)
            if (newRate != null && currency.code != ignoredCurrencyCode) {
                currency.amount =
                    mergeAmounts(currency.amount, newRate.multiply(baseCurrencyAmount))
            } else if (currency.code == baseCurrencyCode) {
                currency.amount = mergeAmounts(currency.amount, baseCurrencyAmount)
            }
        }
    }

    private fun mergeAmounts(originalAmountString: String, newAmount: BigDecimal): String =
        when {
            originalAmountString.isNotBlank() && BigDecimal(originalAmountString).compareTo(
                newAmount
            ) == 0 -> originalAmountString
            newAmount.compareTo(BigDecimal.ZERO) == 0 -> ""
            else -> newAmount.asFormattedString(SCALE)
        }


    fun onCurrencyAmountChange(code: String, newAmount: String?) {
        updateBaseCurrencyAmount(code, newAmount)
        currencies.find { it.code == code }?.amount = newAmount ?: ""
        updateCurrencies(code)
        viewState.showCurrencies(currencies)
    }

    private fun updateBaseCurrencyAmount(code: String, newAmountString: String?) {
        baseCurrencyAmount = if (newAmountString?.isNotBlank() == true) {
            val rate = rates?.get(code) ?: BigDecimal.ONE
            BigDecimal(newAmountString).divide(rate, SCALE, RoundingMode.HALF_EVEN)
        } else {
            BigDecimal("0")
        }
    }

    fun onBaseCurrencyChange(code: String, newAmountString: String?) {
        presenterScope.launch {
            baseCurrencyCode = code
            baseCurrencyAmount = if (newAmountString?.isNotBlank() == true) {
                BigDecimal(newAmountString)
            } else {
                BigDecimal("0")
            }
            moveBaseCurrencyTop()
            currencyInteractor.onBaseCurrencyChange(baseCurrencyCode)
        }
    }

    private fun moveBaseCurrencyTop() {
        val newBaseCurrencyIndex =
            currencies.indexOfFirst { currency -> currency.code == baseCurrencyCode }
        if (newBaseCurrencyIndex != -1 && newBaseCurrencyIndex != 0) {
            val newBaseCurrency = currencies[newBaseCurrencyIndex]
            currencies.removeAt(newBaseCurrencyIndex)
            currencies.add(0, newBaseCurrency)
        }
        viewState.moveTopFrom(newBaseCurrencyIndex)
    }
}
