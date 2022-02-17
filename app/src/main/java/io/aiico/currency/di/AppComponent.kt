package io.aiico.currency.di

import dagger.Component

@Component(modules = [ApiModule::class])
interface AppComponent : ConverterComponent.ConverterDependencies
