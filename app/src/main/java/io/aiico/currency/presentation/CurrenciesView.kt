package io.aiico.currency.presentation

import io.aiico.currency.entity.CurrencyModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface CurrenciesView : MvpView {

    fun showCurrencies(currencies: List<CurrencyModel>)
}