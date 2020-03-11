package com.ekezet.xrated.base.parts.itemPicker

import com.ekezet.xrated.base.arch.IPresenter
import com.ekezet.xrated.base.arch.IView
import com.ekezet.xrated.base.arch.Part
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerSpec.View

/**
 * @author kiri
 */
interface ItemPickerSpec {
    interface View : IView {
        val items: ArrayList<Pickable>
        val selected: Pickable?
        var inputText: String

        fun showFiltered(query: CharSequence)
        fun finishWithResult(item: Pickable?)
        fun setPickableItems(items: List<Pickable>)

        interface Presenter : IPresenter<View> {
            fun onSearchInitiated(inputText: String)
        }
    }

    interface ItemView : IView {
        var item: Pickable
        var isItemSelected: Boolean

        fun setOnClickListener(listener: (android.view.View) -> Unit)

        interface Presenter : IPresenter<View> {
            fun onBindListItem(itemView: ItemView, item: Pickable)
            fun onItemClicked(item: Pickable)
        }
    }
}

typealias ItemPickerPart = Part<View, View.Presenter, Nothing>
