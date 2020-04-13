package io.aiico.currency.presentation

import io.aiico.currency.di.AppComponent

interface ActivityComponentContainer {

    fun onCreateActivityComponent(appComponent: AppComponent)
}