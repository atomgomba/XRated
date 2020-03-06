package com.ekezet.xrated.parts.home.parts.progressIndicator.di

import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorInteractor
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorPart
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorPresenter
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorSpec.Interactor
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorSpec.View
import com.ekezet.xrated.parts.home.parts.progressIndicator.di.ProgressIndicatorModule.Binder
import com.ekezet.xrated.parts.home.parts.progressIndicator.views.ProgressIndicatorView
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
class ProgressIndicatorModule {
    @Module
    abstract class Binder {
        @Binds @ActivityScope abstract fun bindView(view: ProgressIndicatorView): View
        @Binds @ActivityScope abstract fun bindInteractor(interactor: ProgressIndicatorInteractor): Interactor
        @Binds @ActivityScope abstract fun bindPresenter(presenter: ProgressIndicatorPresenter): View.Presenter
    }

    @Provides @ActivityScope fun providePart(view: View, interactor: Interactor, presenter: View.Presenter) =
        ProgressIndicatorPart(view, interactor, presenter, null)
}
