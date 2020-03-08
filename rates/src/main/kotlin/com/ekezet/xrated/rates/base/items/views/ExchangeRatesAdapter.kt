package com.ekezet.xrated.rates.base.items.views

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ekezet.xrated.base.views.VH
import com.ekezet.xrated.rates.base.items.ExchangeRateListItemSpec.View
import com.ekezet.xrated.rates.base.items.viewmodels.ExchangeRateListItem

/**
 * @author kiri
 */
open class ExchangeRatesAdapter constructor(
    private val itemViewPresenter: View.Presenter
) : RecyclerView.Adapter<VH>() {
    val rates: MutableList<ExchangeRateListItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ExchangeRateItemView(parent.context))

    override fun getItemCount() = rates.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = rates[position]
        itemViewPresenter.onBindListItem(holder.itemView as View, item)
        (holder.itemView as ExchangeRateItemView).apply {
            setOnFavoriteButtonClickListener {
                itemViewPresenter.onFavoriteButtonClicked(item)
            }
            setOnCopyButtonClickListener {
                itemViewPresenter.onCopyButtonClicked(item)
            }
            setOnLongPressListener {
                itemViewPresenter.onBaseCurrencySelected(item)
                return@setOnLongPressListener true
            }
        }
    }

    fun setItems(newItems: List<ExchangeRateListItem>) {
        val results = DiffUtil.calculateDiff(
            DiffCallback(
                rates,
                newItems
            )
        )
        rates.clear()
        rates.addAll(newItems)
        results.dispatchUpdatesTo(this)
    }

    class DiffCallback(
        private val oldItems: List<ExchangeRateListItem>,
        private val newItems: List<ExchangeRateListItem>
    ) : DiffUtil.Callback() {
        override fun areContentsTheSame(oldIndex: Int, newIndex: Int) =
            oldItems[oldIndex] == newItems[newIndex]

        override fun areItemsTheSame(oldIndex: Int, newIndex: Int) =
            oldItems[oldIndex] == newItems[newIndex]

        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size
    }
}
