package com.ekezet.xrated.parts.home.parts.infoPage.di

import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPageInteractor
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPagePart
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPagePresenter
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPageSpec.Interactor
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPageSpec.View
import com.ekezet.xrated.parts.home.parts.infoPage.di.InfoPageModule.Binder
import com.ekezet.xrated.parts.home.parts.infoPage.views.InfoPageView
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
class InfoPageModule {
    @Module
    abstract class Binder {
        @Binds @ActivityScope abstract fun bindView(view: InfoPageView): View
        @Binds @ActivityScope abstract fun bindInteractor(interactor: InfoPageInteractor): Interactor
        @Binds @ActivityScope abstract fun bindPresenter(presenter: InfoPagePresenter): View.Presenter
    }

    @Provides @ActivityScope fun providePart(view: View, interactor: Interactor, presenter: View.Presenter) =
        InfoPagePart(view, interactor, presenter, null)
}
