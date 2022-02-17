package io.aiico.currency.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.aiico.currency.R
import io.aiico.currency.di.ActivityComponentContainer
import io.aiico.currency.di.AppComponent
import io.aiico.currency.di.ConverterComponent
import io.aiico.currency.di.DaggerConverterComponent
import io.aiico.currency.presentation.list.CurrencyAdapter
import kotlinx.android.synthetic.main.activity_currencies.*

class CurrenciesActivity : AppCompatActivity(), ActivityComponentContainer {

    private lateinit var component: ConverterComponent
    private val viewModel by viewModels<ConverterViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                component.factory.create() as T
        }
    }

    private val adapter = CurrencyAdapter(
        { code, amount ->
            viewModel.onCurrencyAmountChange(code, amount)
        },
        { code, amount ->
            viewModel.onBaseCurrencyChange(code, amount)
        }
    )

    override fun onCreateComponent(appComponent: AppComponent) {
        component = DaggerConverterComponent.factory().create(appComponent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currencies)
        currencyRecyclerView.adapter = adapter
    }

//    override fun showCurrencies(currencies: List<CurrencyModel>) {
//        adapter.submitList(currencies)
//    }

//    override fun moveTopFrom(from: Int) {
//        adapter.moveTopFrom(from)
//    }
}
