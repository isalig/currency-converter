package io.aiico.currency.di

import dagger.Component

@Component(modules = [DataModule::class])
interface AppComponent : ConverterComponent.ConverterDependencies
