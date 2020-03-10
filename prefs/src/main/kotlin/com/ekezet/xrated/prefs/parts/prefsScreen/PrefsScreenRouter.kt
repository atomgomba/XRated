package com.ekezet.xrated.prefs.parts.prefsScreen

import com.ekezet.xrated.base.BaseRouter
import com.ekezet.xrated.base.di.FragmentScope
import com.ekezet.xrated.base.parts.itemPicker.Pickable
import com.ekezet.xrated.base.parts.itemPicker.views.ItemPickerActivity
import com.ekezet.xrated.base.views.Interrogator
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Companion.REQUEST_CODE_LANGUAGE_PICKER
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Router
import javax.inject.Inject

/**
 * @author kiri
 */
@FragmentScope
class PrefsScreenRouter @Inject constructor(
    private val interrogator: Interrogator
) : BaseRouter(), Router {
    override fun startItemPicker(items: List<Pickable>, title: CharSequence) {
        interrogator.startActivityForResult(
            ItemPickerActivity.newIntent(context, ArrayList(items), title), REQUEST_CODE_LANGUAGE_PICKER
        )
    }
}
