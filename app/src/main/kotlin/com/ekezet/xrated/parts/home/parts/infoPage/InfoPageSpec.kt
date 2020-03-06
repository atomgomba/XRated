package com.ekezet.xrated.parts.home.parts.infoPage

import com.ekezet.xrated.base.arch.IInteractor
import com.ekezet.xrated.base.arch.IPresenter
import com.ekezet.xrated.base.arch.IView
import com.ekezet.xrated.base.arch.Part
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPageSpec.Interactor
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPageSpec.View
import org.joda.time.DateTime

/**
 * @author kiri
 */
interface InfoPageSpec {
    interface View : IView {
        var publishedAt: CharSequence
        var downloadedAt: CharSequence
        var version: CharSequence
        var buildId: CharSequence
        var updateHour: Int

        interface Presenter : IPresenter<View> {
            fun onClearCacheButtonClicked()
        }
    }

    interface Interactor : IInteractor<Interactor.Presenter> {
        fun clearCache()

        interface Presenter : IPresenter<View> {
            fun onDatesAvailable(publishedAt: DateTime?, updatedAt: DateTime?)
        }
    }
}

typealias InfoPagePart = Part<View, View.Presenter, Interactor.Presenter>
