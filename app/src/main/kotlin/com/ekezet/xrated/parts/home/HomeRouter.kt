package com.ekezet.xrated.parts.home

import com.ekezet.xrated.base.BaseRouter
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.parts.home.HomeSpec.Router
import com.ekezet.xrated.prefs.parts.prefsScreen.views.PreferencesActivity
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class HomeRouter @Inject constructor() : BaseRouter(), Router {
    override fun startPreferencesActivity() {
        context.startActivity(PreferencesActivity.newIntent(context))
    }
}
