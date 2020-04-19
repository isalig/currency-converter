package io.aiico.currency.presentation

import android.os.Bundle
import io.aiico.currency.R
import io.aiico.currency.di.ActivityComponentContainer
import io.aiico.currency.di.AppComponent
import io.aiico.currency.di.DaggerAppComponent
import io.aiico.currency.presentation.entity.CurrencyViewModel
import io.aiico.currency.presentation.list.CurrencyAdapter
import kotlinx.android.synthetic.main.activity_currencies.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CurrenciesActivity : MvpAppCompatActivity(), CurrenciesView,
    ActivityComponentContainer {

    private val adapter = CurrencyAdapter(
        { code, amount ->
            presenter.onCurrencyAmountChange(code, amount)
        },
        { code, amount ->
            presenter.onBaseCurrencyChange(code, amount)
        }
    )

    @Inject
    lateinit var presenterProvider: Provider<CurrenciesPresenter>

    private val presenter: CurrenciesPresenter by moxyPresenter { presenterProvider.get() }

    override fun onCreateComponent(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAppComponent.create().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currencies)
        currencyRecyclerView.adapter = adapter
    }

    override fun showCurrencies(currencies: List<CurrencyViewModel>) {
        adapter.submitList(currencies)
    }

    override fun moveTopFrom(from: Int) {
        adapter.moveTopFrom(from)
    }
}
