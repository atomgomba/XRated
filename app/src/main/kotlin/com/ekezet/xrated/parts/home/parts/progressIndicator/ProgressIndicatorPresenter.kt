package com.ekezet.xrated.parts.home.parts.progressIndicator

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.R
import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorSpec.Interactor
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorSpec.View
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class ProgressIndicatorPresenter @Inject constructor() : BasePresenter<View, Interactor, Nothing>(),
    Interactor.Presenter, View.Presenter {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        view.setup(R.layout.view_progress_indicator)
    }

    override fun onLoadingStarted() {
        view.startLoading()
    }

    override fun onLoadingFinished() {
        view.finishLoading()
    }
}
