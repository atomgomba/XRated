package com.ekezet.xrated.prefs.parts.prefsScreen.di

import android.content.Context
import com.ekezet.xrated.base.di.FragmentScope
import com.ekezet.xrated.base.views.Interrogator
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenInteractor
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenPart
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenPresenter
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenRouter
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.*
import com.ekezet.xrated.prefs.parts.prefsScreen.di.PrefsFragmentModule.Binder
import com.ekezet.xrated.prefs.parts.prefsScreen.views.PreferencesActivity
import com.ekezet.xrated.prefs.parts.prefsScreen.views.PreferencesFragment
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
object PrefsFragmentModule {
    @Module
    abstract class Binder {
        @Binds @FragmentScope abstract fun bindContext(activity: PreferencesActivity): Context
        @Binds @FragmentScope abstract fun bindView(fragment: PreferencesFragment): View
        @Binds @FragmentScope abstract fun bindInterrogator(fragment: PreferencesFragment): Interrogator
        @Binds @FragmentScope abstract fun bindInteractor(interactor: PrefsScreenInteractor): Interactor
        @Binds @FragmentScope abstract fun bindPresenter(presenter: PrefsScreenPresenter): View.Presenter
        @Binds @FragmentScope abstract fun bindRouter(router: PrefsScreenRouter): Router
    }

    @Provides @FragmentScope fun providePart(
        view: View,
        interactor: Interactor,
        presenter: View.Presenter,
        router: Router
    ) =
        PrefsScreenPart(view, interactor, presenter, router)
}
