package io.aiico.currency.di

import dagger.Component
import io.aiico.currency.presentation.CurrenciesActivity

@Component(modules = [ApiModule::class])
interface AppComponent {

    fun inject(activity: CurrenciesActivity)
}