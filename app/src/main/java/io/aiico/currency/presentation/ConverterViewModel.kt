package io.aiico.currency.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.aiico.currency.BuildConfig
import io.aiico.currency.domain.CurrencyInteractor
import io.aiico.currency.presentation.entity.ConverterViewState
import io.aiico.currency.presentation.entity.CurrencyModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

private const val SCALE = 2

class ConverterViewModel @AssistedInject constructor(
    private val currencyInteractor: CurrencyInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<ConverterViewState?>(null)
    private val state: Flow<ConverterViewState> = _state.asStateFlow().filterNotNull()

    private var baseCurrencyCode = BuildConfig.DEFAULT_CURRENCY
    private var baseCurrencyAmount = BigDecimal(BuildConfig.DEFAULT_AMOUNT)
    private var currencies = ArrayList<CurrencyModel>()
    private var rates: Map<String, BigDecimal>? = null

    init {
        viewModelScope.launch {
            currencyInteractor
                .getCurrenciesChange()
                .onEach(::onUpdateRates)
                .catch { error -> Log.e("Currency App", error.message, error) }

            currencyInteractor.onBaseCurrencyChange(baseCurrencyCode)
        }
    }

    private suspend fun onUpdateRates(newRates: Map<String, BigDecimal>) {
        rates = newRates
        if (currencies.isEmpty()) {
            initCurrencies()
        } else {
            updateCurrencies(null)
        }

        _state.emit(ConverterViewState(currencies))
    }

    private fun initCurrencies() {
        currencies.add(fromAmount(baseCurrencyCode, baseCurrencyAmount))
        rates?.forEach { entry ->
            currencies.add(fromAmount(entry.key, entry.value.multiply(baseCurrencyAmount)))
        }
    }

    private fun fromAmount(code: String, amount: BigDecimal) = CurrencyModel(
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
        viewModelScope.launch {
            _state.emit(ConverterViewState(currencies))
        }
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
        viewModelScope.launch {
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

    private suspend fun moveBaseCurrencyTop() {
        val newBaseCurrencyIndex =
            currencies.indexOfFirst { currency -> currency.code == baseCurrencyCode }
        if (newBaseCurrencyIndex != -1 && newBaseCurrencyIndex != 0) {
            val newBaseCurrency = currencies[newBaseCurrencyIndex]
            currencies.removeAt(newBaseCurrencyIndex)
            currencies.add(0, newBaseCurrency)
        }
//        viewState.moveTopFrom(newBaseCurrencyIndex)
    }

    @AssistedFactory
    interface Factory {
        fun create(): ConverterViewModel
    }
}
