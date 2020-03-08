package com.ekezet.xrated.prefs.parts.prefsScreen.data

import com.ekezet.xrated.base.parts.itemPicker.Pickable
import kotlinx.android.parcel.Parcelize
import java.util.Locale

/**
 * @author kiri
 */
@Parcelize
data class Language(
    val isoCode2: String,
    private val displayName: String
) : Pickable {
    override val pickerTitle
        get() = displayName

    companion object {
        fun list(): List<Language> =
            Locale.getAvailableLocales().asSequence()
                .map { Language(it.language, it.displayLanguage) }
                .distinctBy { it.displayName }
                .sortedBy { it.displayName }
                .toList()
    }
}
