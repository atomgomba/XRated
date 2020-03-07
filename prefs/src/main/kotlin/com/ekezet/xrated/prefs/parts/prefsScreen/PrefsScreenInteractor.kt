package com.ekezet.xrated.prefs.parts.prefsScreen

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.BaseInteractor
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Interactor
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class PrefsScreenInteractor @Inject constructor() : BaseInteractor<Interactor.Presenter>(), Interactor {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }
}
