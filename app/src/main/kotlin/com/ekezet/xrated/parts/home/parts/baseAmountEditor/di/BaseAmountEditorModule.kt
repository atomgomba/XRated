package com.ekezet.xrated.parts.home.parts.baseAmountEditor.di

import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.BaseAmountEditorInteractor
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.BaseAmountEditorPart
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.BaseAmountEditorPresenter
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.BaseAmountEditorSpec.Interactor
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.BaseAmountEditorSpec.View
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.di.BaseAmountEditorModule.Binder
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.views.BaseAmountEditorView
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
object BaseAmountEditorModule {
    @Module
    abstract class Binder {
        @Binds @ActivityScope abstract fun bindView(view: BaseAmountEditorView): View
        @Binds @ActivityScope abstract fun bindInteractor(interactor: BaseAmountEditorInteractor): Interactor
        @Binds @ActivityScope abstract fun bindPresenter(presenter: BaseAmountEditorPresenter): View.Presenter
    }

    @Provides @ActivityScope fun providePart(view: View, interactor: Interactor, presenter: View.Presenter) =
        BaseAmountEditorPart(view, interactor, presenter, null)
}
