package com.ekezet.xrated.base.parts.itemPicker.views

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.ekezet.xrated.base.R
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerSpec.ItemView
import com.ekezet.xrated.base.parts.itemPicker.Pickable
import com.ekezet.xrated.base.views.BaseView
import kotlinx.android.synthetic.main.list_item_pickable.view.*

/**
 * @author kiri
 */
class PickableItemView(context: Context) : BaseView(context), ItemView {
    init {
        inflate(context, R.layout.list_item_pickable, this)
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    override var item: Pickable
        get() = throw IllegalAccessException("Reading this field is not allowed")
        set(value) {
            titleText.text = value.pickerTitle
        }

    override var isItemSelected: Boolean
        get() = throw IllegalAccessException("Reading this field is not allowed")
        set(value) {
            titleText.typeface = if (value) {
                Typeface.DEFAULT_BOLD
            } else {
                Typeface.DEFAULT
            }
        }

    @Suppress("RedundantOverride")
    override fun setOnClickListener(listener: (View) -> Unit) {
        super.setOnClickListener(listener)
    }
}
