package com.ekezet.xrated.base.parts.itemPicker.views

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerSpec.ItemView
import com.ekezet.xrated.base.parts.itemPicker.Pickable
import com.ekezet.xrated.base.views.VH
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class ItemPickerAdapter @Inject constructor(
    private val presenter: ItemView.Presenter
) : RecyclerView.Adapter<VH>(), Filterable {
    private val items: MutableList<Pickable> = ArrayList()
    private val origItems: MutableList<Pickable> = ArrayList()

    init {
        setHasStableIds(true)
    }

    private val searchFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?) =
            FilterResults().apply {
                values = if (constraint?.isNotBlank() == true) {
                    origItems.filter {
                        it.pickerTitle.contains(constraint, true)
                    }
                } else {
                    origItems
                }
            }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            @Suppress("UNCHECKED_CAST")
            setFilteredItems(results.values as List<Pickable>)
        }
    }

    fun setItems(newItems: List<Pickable>) {
        origItems.clear()
        origItems.addAll(newItems)
        setFilteredItems(newItems)
    }

    private fun setFilteredItems(newItems: List<Pickable>) {
        val results = DiffUtil.calculateDiff(DiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        results.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(PickableItemView(parent.context))

    override fun onBindViewHolder(holder: VH, position: Int) {
        presenter.onBindListItem(holder.itemView as ItemView, items[position])
    }

    override fun getItemId(position: Int) = items[position].id

    override fun getItemCount() = items.size

    override fun getFilter(): Filter = searchFilter

    private val Pickable.id: Long
        get() = pickerTitle.toString().toCharArray().contentHashCode().toLong()

    private class DiffCallback(
        private val oldList: List<Pickable>,
        private val newList: List<Pickable>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldIndex: Int, newIndex: Int) =
            oldList[oldIndex] == newList[newIndex]

        override fun areContentsTheSame(oldIndex: Int, newIndex: Int) =
            oldList[oldIndex].pickerTitle == newList[newIndex].pickerTitle

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size
    }
}
