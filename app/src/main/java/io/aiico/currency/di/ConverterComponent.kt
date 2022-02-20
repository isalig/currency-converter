package io.aiico.currency.di

import dagger.Component
import io.aiico.currency.domain.usecase.GetExchangeRatesUseCase
import io.aiico.currency.ui.ConverterViewModel

@Component(dependencies = [ConverterComponent.ConverterDependencies::class])
interface ConverterComponent {

    val factory: ConverterViewModel.Factory

    @Component.Factory
    interface Factory {
        fun create(deps: ConverterDependencies): ConverterComponent
    }

    interface ConverterDependencies {
        val getExchangeRatesUseCase: GetExchangeRatesUseCase
    }
}
