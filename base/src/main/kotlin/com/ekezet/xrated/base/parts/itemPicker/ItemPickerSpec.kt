package com.ekezet.xrated.base.parts.itemPicker

import com.ekezet.xrated.base.arch.IPresenter
import com.ekezet.xrated.base.arch.IRouter
import com.ekezet.xrated.base.arch.IView
import com.ekezet.xrated.base.arch.Part
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerSpec.View

/**
 * @author kiri
 */
interface ItemPickerSpec {
    interface View : IView {
        val items: ArrayList<Pickable>
        var inputText: String

        fun finishWithResult(item: Pickable?)
        fun setPickableItems(items: List<Pickable>)

        interface Presenter : IPresenter<View> {
            fun onSearchInitiated(inputText: String)
            fun onItemPicked(item: Pickable)
        }
    }

    interface Router : IRouter
}

typealias ItemPickerPart = Part<View, View.Presenter, Nothing>
