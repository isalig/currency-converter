package io.aiico.currency.presentation

import android.util.Log
import io.aiico.currency.BuildConfig
import io.aiico.currency.CurrencyInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class CurrenciesPresenter @Inject constructor(
    private val currencyInteractor: CurrencyInteractor
) : MvpPresenter<CurrenciesView>() {

    private val compositeDisposable = CompositeDisposable()
    private val baseCurrencyChangePublisher = PublishSubject.create<String>()

    override fun onFirstViewAttach() {
        compositeDisposable += baseCurrencyChangePublisher
            .distinctUntilChanged()
            .switchMap { code -> loadCurrenciesInterval(code) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { currencies ->
                    viewState.showCurrencies(currencies)
                },
                { error ->
                    Log.e("Currency App", error.message, error)
                }
            )

        baseCurrencyChangePublisher.onNext(BuildConfig.DEFAULT_CURRENCY)
    }

    private fun loadCurrenciesInterval(code: String) =
        Observable
            .interval(0, 1, TimeUnit.SECONDS)
            .concatMapSingle { currencyInteractor.getCurrencies(code) }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}
