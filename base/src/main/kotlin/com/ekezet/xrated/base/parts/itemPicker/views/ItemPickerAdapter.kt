package com.ekezet.xrated.base.parts.itemPicker.views

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerSpec.View
import com.ekezet.xrated.base.parts.itemPicker.Pickable
import com.ekezet.xrated.base.views.VH
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class ItemPickerAdapter @Inject constructor(
    private val presenter: View.Presenter
) : RecyclerView.Adapter<VH>() {
    private val items: MutableList<Pickable> = ArrayList()

    fun setItems(newItems: List<Pickable>) {
        val results = DiffUtil.calculateDiff(DiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        results.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(PickableItemView(parent.context))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val pickable = items[position]
        (holder.itemView as PickableItemView).apply {
            setOnClickListener { presenter.onItemPicked(pickable) }
            item = pickable
        }
    }

    override fun getItemCount() = items.size

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
