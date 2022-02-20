package io.aiico.currency.di

import dagger.Component
import io.aiico.currency.domain.GetCurrenciesUseCase
import io.aiico.currency.presentation.ConverterViewModel

@Component(dependencies = [ConverterComponent.ConverterDependencies::class])
interface ConverterComponent {

    val factory: ConverterViewModel.Factory

    @Component.Factory
    interface Factory {
        fun create(deps: ConverterDependencies): ConverterComponent
    }

    interface ConverterDependencies {
        val converterInteractor: GetCurrenciesUseCase
    }
}
