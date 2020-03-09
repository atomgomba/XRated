package com.ekezet.xrated.base.parts.itemPicker

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.base.R
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerSpec.View
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class ItemPickerPresenter @Inject constructor() : BasePresenter<View, Nothing, Nothing>(), View.Presenter {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        view.setup(R.layout.activity_item_picker)
        view.setPickableItems(view.items)
    }

    override fun onSearchInitiated(inputText: String) {
        view.showFiltered(inputText)
    }

    override fun onItemPicked(item: Pickable) {
        view.finishWithResult(item)
    }
}
