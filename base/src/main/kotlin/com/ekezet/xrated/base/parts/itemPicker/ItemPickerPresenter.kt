package com.ekezet.xrated.base.parts.itemPicker

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.base.R
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerSpec.ItemView
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerSpec.View
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class ItemPickerPresenter @Inject constructor() : BasePresenter<View, Nothing, Nothing>(), View.Presenter,
    ItemView.Presenter {
    private var selected: Pickable? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        view.setup(R.layout.activity_item_picker)
        view.setPickableItems(view.items)
        selected = view.selected
    }

    override fun onSearchInitiated(inputText: String) {
        view.showFiltered(inputText)
    }

    override fun onItemClicked(item: Pickable) {
        view.finishWithResult(item)
    }

    override fun onBindListItem(itemView: ItemView, item: Pickable) {
        itemView.item = item
        itemView.isItemSelected = item == selected
        itemView.setOnClickListener { onItemClicked(item) }
    }
}
