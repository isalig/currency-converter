package io.aiico.currency

import android.app.Application
import io.aiico.currency.di.AppComponent
import io.aiico.currency.di.DaggerAppComponent

@Suppress("unused")
class App : Application() {

  val appComponent: AppComponent = DaggerAppComponent.create()
}
