package io.aiico.currency.presentation

import io.aiico.currency.presentation.entity.CurrencyViewModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface CurrenciesView : MvpView {

    fun showCurrencies(currencies: List<CurrencyViewModel>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun moveTopFrom(from: Int)
}