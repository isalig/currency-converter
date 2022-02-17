package io.aiico.currency.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.aiico.currency.R
import io.aiico.currency.presentation.entity.CurrencyModel

class CurrencyAdapter(
    private val amountChangeListener: (code: String, amount: String?) -> Unit,
    private val baseCurrencyChangeListener: (code: String, amount: String?) -> Unit
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    private val items = ArrayList<CurrencyModel>()

    fun submitList(newItems: List<CurrencyModel>) {
        val isInsertion = items.isEmpty()
        items.clear()
        items.addAll(newItems)
        if (isInsertion) {
            notifyItemRangeInserted(0, itemCount)
        } else {
            notifyItemRangeChanged(0, itemCount, Unit)
        }
    }

    fun moveTopFrom(from: Int) {
        notifyItemMoved(from, 0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CurrencyViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_currency, parent, false),
            amountChangeListener,
            baseCurrencyChangeListener
        )

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            holder.updateAmount(items[position].amount)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
