package com.ekezet.xrated.prefs.parts.prefsScreen

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.prefs.R
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Interactor
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Router
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.View
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class PrefsScreenPresenter @Inject constructor() : BasePresenter<View, Interactor, Router>(), View.Presenter,
    Interactor.Presenter {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        view.setup(R.layout.activity_preferences)
    }

    override fun onLocaleSelected(language: String) {
        TODO("not implemented")
    }

    override fun onLocaleChanged(language: String) {
        TODO("not implemented")
    }
}
