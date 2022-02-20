package io.aiico.currency.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.aiico.currency.BuildConfig
import io.aiico.currency.domain.GetCurrenciesUseCase
import io.aiico.currency.presentation.entity.ConverterViewState
import io.aiico.currency.presentation.entity.CurrencyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.math.BigDecimal

private const val SCALE = 2

class ConverterViewModel @AssistedInject constructor(
    private val getCurrencies: GetCurrenciesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ConverterViewState?>(null)
    val state: Flow<ConverterViewState> = _state.asStateFlow().filterNotNull()

    init {
        viewModelScope.launch {
            val rates = runCatching { getCurrencies(BuildConfig.DEFAULT_CURRENCY) }
                .onFailure { error -> Log.e("Currency App", error.message, error) }
                .getOrThrow()
                .map { (key, value) -> fromAmount(key, value) }
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
