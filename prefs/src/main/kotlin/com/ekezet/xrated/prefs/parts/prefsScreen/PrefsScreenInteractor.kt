package com.ekezet.xrated.prefs.parts.prefsScreen

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.BaseInteractor
import com.ekezet.xrated.base.di.FragmentScope
import com.ekezet.xrated.base.utils.PrefsManager
import com.ekezet.xrated.base.utils.PrefsManager.Companion.PREF_NUM_FORMAT_LANGUAGE
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Interactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author kiri
 */
@FragmentScope
class PrefsScreenInteractor @Inject constructor(
    private val prefsManager: PrefsManager
) : BaseInteractor<Interactor.Presenter>(), Interactor {
    override val coroutineContext = Dispatchers.Main

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        launch {
            prefsManager.changesChannel.consumeEach {
                if (it == PREF_NUM_FORMAT_LANGUAGE) {
                    presenter.onNumberFormatChanged()
                }
            }
        }
    }
}
