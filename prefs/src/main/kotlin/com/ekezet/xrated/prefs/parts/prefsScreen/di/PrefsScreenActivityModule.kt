package com.ekezet.xrated.prefs.parts.prefsScreen.di

import android.content.Context
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenInteractor
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenPart
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenPresenter
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenRouter
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Interactor
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Interactor.Presenter
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Router
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.View
import com.ekezet.xrated.prefs.parts.prefsScreen.di.PrefsScreenActivityModule.Binder
import com.ekezet.xrated.prefs.parts.prefsScreen.views.PreferencesActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * @author kiri
 */
@Module(
    includes = [
        Binder::class
    ]
)
class PrefsScreenActivityModule {
    @Module
    abstract class Binder {
        @Binds @ActivityScope abstract fun bindContext(activity: PreferencesActivity): Context
        @Binds @ActivityScope abstract fun bindView(activity: PreferencesActivity): View
        @Binds @ActivityScope abstract fun bindInteractor(interactor: PrefsScreenInteractor): Interactor
        @Binds @ActivityScope abstract fun bindPresenter(presenter: PrefsScreenPresenter): View.Presenter
        @Binds @ActivityScope abstract fun bindRouter(router: PrefsScreenRouter): Router
    }

    @Provides @ActivityScope fun providePart(view: View, interactor: Interactor, presenter: View.Presenter, router: Router) =
        PrefsScreenPart(view, interactor, presenter, router)
}
