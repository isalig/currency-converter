package io.aiico.currency.presentation.list

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import io.aiico.currency.presentation.entity.CurrencyModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_currency.*
import kotlinx.android.synthetic.main.list_item_currency.view.*


class CurrencyViewHolder(
    override val containerView: View,
    private val amountChangeListener: (code: String, amount: String?) -> Unit,
    private val baseCurrencyChangeListener: (code: String, amount: String?) -> Unit
) : RecyclerView.ViewHolder(containerView),
    LayoutContainer,
    TextWatcher,
    View.OnClickListener {

    init {
        itemView.currencyAmountEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                itemView.callOnClick()
            }
        }
        itemView.setOnClickListener(this)
    }

    fun bind(currency: CurrencyModel) {
        itemView.tag = currency.code
        updateAmount(currency.amount)
        with(java.util.Currency.getInstance(currency.code)) {
            currencyCodeTextView.text = currency.code
            currencyNameTextView.text = displayName
            currencySignTextView.text = symbol
        }
    }

    fun updateAmount(amount: String) {
        currencyAmountEditText.removeTextChangedListener(this)
        currencyAmountEditText.text?.clear()
        currencyAmountEditText.append(amount)
        if (bindingAdapterPosition == 0) {
            currencyAmountEditText.addTextChangedListener(this)
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        with(prepareDataForCallback()) {
            amountChangeListener.invoke(first, second)
        }
    }

    override fun onClick(v: View?) {
        if (bindingAdapterPosition != 0) {
            currencyAmountEditText.requestFocus()
            showKeyboard(currencyAmountEditText)
            with(prepareDataForCallback()) {
                baseCurrencyChangeListener.invoke(first, second)
            }
        }
    }

    private fun showKeyboard(currencyAmountEditText: EditText) {
        val inputMethodManager: InputMethodManager? =
            getSystemService(currencyAmountEditText.context, InputMethodManager::class.java)
        inputMethodManager?.showSoftInput(currencyAmountEditText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun prepareDataForCallback() = Pair(
        itemView.tag as String,
        currencyAmountEditText.text?.trim()?.toString()
    )
}
