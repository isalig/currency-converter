package io.aiico.currency.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import io.aiico.currency.R
import io.aiico.currency.di.ActivityComponentContainer
import io.aiico.currency.di.AppComponent
import io.aiico.currency.di.ConverterComponent
import io.aiico.currency.di.DaggerConverterComponent
import io.aiico.currency.ui.list.CurrencyAdapter
import kotlinx.android.synthetic.main.activity_currencies.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CurrenciesActivity : AppCompatActivity(), ActivityComponentContainer {

    private lateinit var component: ConverterComponent
    private val viewModel by viewModels<ConverterViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                component.factory.create() as T
        }
    }

    private val adapter = CurrencyAdapter(
        { code, amount -> },
        { code, amount -> }
    )

    override fun onCreateComponent(appComponent: AppComponent) {
        component = DaggerConverterComponent.factory().create(appComponent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currencies)
        currencyRecyclerView.adapter = adapter
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state
                    .map { state -> state.currencies }
                    .collect(adapter::submitList)
            }
        }
    }
}
