package com.ekezet.xrated.base.parts.itemPicker.views

import android.content.Context
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.ekezet.xrated.base.R
import com.ekezet.xrated.base.parts.itemPicker.Pickable
import kotlinx.android.synthetic.main.list_item_pickable.view.*

/**
 * @author kiri
 */
class PickableItemView(context: Context) : ConstraintLayout(context) {
    init {
        inflate(context, R.layout.list_item_pickable, this)
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    var item: Pickable
        get() = throw IllegalAccessException("Reading this field is not allowed")
        set(value) {
            titleText.text = value.pickerTitle
        }
}
