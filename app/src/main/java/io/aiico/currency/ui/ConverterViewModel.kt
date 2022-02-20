package io.aiico.currency.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.aiico.currency.BuildConfig
import io.aiico.currency.domain.model.CurrencyCode
import io.aiico.currency.domain.usecase.GetExchangeRatesUseCase
import io.aiico.currency.ui.mapper.CurrencyModelMapper
import io.aiico.currency.ui.model.ConverterViewState
import io.aiico.currency.ui.model.CurrencyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.math.BigDecimal

private const val SCALE = 2

class ConverterViewModel @AssistedInject constructor(
    private val getCurrencies: GetExchangeRatesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ConverterViewState?>(null)
    val state: Flow<ConverterViewState> = _state.asStateFlow().filterNotNull()

    init {
        viewModelScope.launch {
            val rates = runCatching { getCurrencies(CurrencyCode(BuildConfig.DEFAULT_CURRENCY)) }
                .onFailure { error -> Log.e("Currency App", error.message, error) }
                .getOrThrow()
                .map(CurrencyModelMapper::CurrencyModel)
            _state.emit(ConverterViewState(rates))
        }
    }

    private fun fromAmount(code: String, amount: BigDecimal) = CurrencyModel(
        code,
        amount.asFormattedString(SCALE)
    )

    @AssistedFactory
    interface Factory {
        fun create(): ConverterViewModel
    }
}
