package io.aiico.currency

import android.app.Activity
import android.app.Application
import android.os.Bundle
import io.aiico.currency.di.ActivityComponentContainer
import io.aiico.currency.di.AppComponent
import io.aiico.currency.di.DaggerAppComponent
import io.aiico.currency.ui.ActivityLifecycleCallbacksAdapter

@Suppress("unused")
class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter {
            override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
                (activity as? ActivityComponentContainer)?.onCreateComponent(appComponent)
            }
        })
    }
}
