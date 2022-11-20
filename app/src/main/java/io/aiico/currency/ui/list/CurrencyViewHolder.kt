package io.aiico.currency.ui.list

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import io.aiico.currency.databinding.ListItemCurrencyBinding
import io.aiico.currency.ui.model.CurrencyModel
import java.util.*

class CurrencyViewHolder(
    view: View,
    private val amountChangeListener: (code: String, amount: String?) -> Unit,
    private val baseCurrencyChangeListener: (code: String, amount: String?) -> Unit
) : RecyclerView.ViewHolder(view),
    TextWatcher,
    View.OnClickListener {

    private val viewBinding = ListItemCurrencyBinding.bind(view)

    init {
        with(viewBinding) {
            currencyAmountEditText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    root.callOnClick()
                }
            }
            root.setOnClickListener(this@CurrencyViewHolder)
        }
    }

    fun bind(currency: CurrencyModel) {
        with(viewBinding) {
            root.tag = currency.code
            updateAmount(currency.amount)
            with(Currency.getInstance(currency.code)) {
                currencyCodeTextView.text = currency.code
                currencyNameTextView.text = displayName
                currencySignTextView.text = symbol
            }
        }
    }

    fun updateAmount(amount: String) {
        with(viewBinding) {
            currencyAmountEditText.removeTextChangedListener(this@CurrencyViewHolder)
            currencyAmountEditText.text = amount
            if (bindingAdapterPosition == 0) {
                currencyAmountEditText.addTextChangedListener(this@CurrencyViewHolder)
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        with(viewBinding) {
            with(prepareDataForCallback()) {
                amountChangeListener.invoke(first, second)
            }
        }
    }

    override fun onClick(v: View?) {
        if (bindingAdapterPosition != 0) {
            with(viewBinding) {
                currencyAmountEditText.requestFocus()
                with(prepareDataForCallback()) {
                    baseCurrencyChangeListener.invoke(first, second)
                }
            }
        }
    }

    private fun showKeyboard(currencyAmountEditText: EditText) {
        val inputMethodManager: InputMethodManager? =
            getSystemService(currencyAmountEditText.context, InputMethodManager::class.java)
        inputMethodManager?.showSoftInput(currencyAmountEditText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun ListItemCurrencyBinding.prepareDataForCallback() = Pair(
        itemView.tag as String,
        currencyAmountEditText.text?.trim()?.toString()
    )
}
