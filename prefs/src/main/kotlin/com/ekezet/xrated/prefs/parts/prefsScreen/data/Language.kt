package com.ekezet.xrated.prefs.parts.prefsScreen.data

import com.ekezet.xrated.base.parts.itemPicker.Pickable
import kotlinx.android.parcel.Parcelize
import java.util.Locale

/**
 * @author kiri
 */
@Parcelize
data class Language(
    val locale: Locale,
    override val pickerTitle: CharSequence
) : Pickable {

    companion object {
        fun list(): List<Language> =
            Locale.getAvailableLocales().asSequence()
                .map {
                    if (it.displayCountry.isBlank()) {
                        Language(it, it.displayLanguage)
                    } else {
                        Language(it, "%s (%s)".format(it.displayLanguage, it.displayCountry))
                    }
                }
                .toList()
    }
}
