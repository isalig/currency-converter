package io.aiico.currency.presentation

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.math.BigDecimal
import java.math.RoundingMode

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

fun BigDecimal.asFormattedString(scale: Int): String =
    setScale(scale, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString()
