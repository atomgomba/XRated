package com.ekezet.xrated.parts.home.di

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.di.MENU
import com.ekezet.xrated.parts.home.HomeInteractor
import com.ekezet.xrated.parts.home.HomePart
import com.ekezet.xrated.parts.home.HomePresenter
import com.ekezet.xrated.parts.home.HomeRouter
import com.ekezet.xrated.parts.home.HomeSpec.Interactor
import com.ekezet.xrated.parts.home.HomeSpec.Router
import com.ekezet.xrated.parts.home.HomeSpec.View
import com.ekezet.xrated.parts.home.di.HomeActivityModule.Binder
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.di.BaseAmountEditorModule
import com.ekezet.xrated.parts.home.parts.bottomMenu.di.BottomMenuModule
import com.ekezet.xrated.parts.home.parts.progressIndicator.di.ProgressIndicatorModule
import com.ekezet.xrated.parts.home.views.HomeActivity
import com.ekezet.xrated.prefs.parts.prefsScreen.di.PrefsScreenModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * @author kiri
 */
@Module(
    includes = [
        Binder::class,
        NavigationModule::class,
        BottomMenuModule::class,
        BaseAmountEditorModule::class,
        ProgressIndicatorModule::class
    ]
)
class HomeActivityModule {
    @Module
    abstract class Binder {
        @Binds @ActivityScope abstract fun bindContext(activity: HomeActivity): Context
        @Binds @ActivityScope abstract fun bindView(view: HomeActivity): View
        @Binds @ActivityScope abstract fun bindInteractor(interactor: HomeInteractor): Interactor
        @Binds @ActivityScope abstract fun bindPresenter(presenter: HomePresenter): View.Presenter
        @Binds @ActivityScope abstract fun bindRouter(router: HomeRouter): Router
        @Binds @ActivityScope abstract fun bindLifecycleOwner(activity: HomeActivity): LifecycleOwner
    }

    @Provides @ActivityScope fun providePart(
        view: View,
        interactor: Interactor,
        presenter: View.Presenter,
        router: Router
    ) =
        HomePart(view, interactor, presenter, router)

    @Provides @Named(MENU) @ActivityScope fun provideNavigationChannel() = NavigationChannel()
}
