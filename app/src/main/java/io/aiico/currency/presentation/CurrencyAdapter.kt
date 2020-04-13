package io.aiico.currency.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import io.aiico.currency.R
import io.aiico.currency.entity.CurrencyModel

class CurrencyAdapter : ListAdapter<CurrencyModel, CurrencyViewHolder>(CurrencyItemCallback()) {

//    val items = ArrayList<Currency>()

//    fun submitList(newItems: List<Currency>) {
//        val isInsertion = items.isEmpty()
//        items.clear()
//        items.addAll(newItems)
//        if (isInsertion) {
//            notifyItemRangeInserted(0, itemCount)
//        } else {
//            notifyItemRangeChanged(0, itemCount)
//        }
//    }

//    fun moveThirdToTop() {
//        notifyItemMoved(2, 0)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_currency, parent, false)
        )
//            .apply {
//            itemView.setOnClickListener {
//                onCurrencyClick.invoke(items[adapterPosition])
//                moveThirdToTop()
//            }
//        }
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
