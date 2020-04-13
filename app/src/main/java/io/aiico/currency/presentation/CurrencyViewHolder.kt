package io.aiico.currency.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.aiico.currency.entity.CurrencyModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_currency.*

class CurrencyViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(currency: CurrencyModel) {
        currencyAmountEditText.setText("${currency.rate}")
        with(java.util.Currency.getInstance(currency.code)) {
            currencyCodeTextView.text = currency.code
            currencyNameTextView.text = displayName
            currencySignTextView.text = symbol
        }
    }
}