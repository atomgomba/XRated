package com.ekezet.xrated.parts.home.parts.progressIndicator

import com.ekezet.xrated.base.arch.IInteractor
import com.ekezet.xrated.base.arch.IPresenter
import com.ekezet.xrated.base.arch.IView
import com.ekezet.xrated.base.arch.Part
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorSpec.Interactor
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorSpec.View

/**
 * @author kiri
 */
interface ProgressIndicatorSpec {
    interface View : IView {
        fun startLoading()
        fun finishLoading()

        interface Presenter : IPresenter<View>
    }

    interface Interactor : IInteractor<Interactor.Presenter> {
        interface Presenter : IPresenter<View> {
            fun onLoadingStarted()
            fun onLoadingFinished()
        }
    }
}

typealias ProgressIndicatorPart = Part<View, View.Presenter, Interactor.Presenter>
