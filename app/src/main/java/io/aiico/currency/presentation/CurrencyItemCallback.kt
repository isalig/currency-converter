package io.aiico.currency.presentation

import androidx.recyclerview.widget.DiffUtil
import io.aiico.currency.entity.CurrencyModel

class CurrencyItemCallback : DiffUtil.ItemCallback<CurrencyModel>() {

    override fun areContentsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean = false

    override fun areItemsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean =
        oldItem.code == newItem.code

    override fun getChangePayload(oldItem: CurrencyModel, newItem: CurrencyModel) = Unit
}