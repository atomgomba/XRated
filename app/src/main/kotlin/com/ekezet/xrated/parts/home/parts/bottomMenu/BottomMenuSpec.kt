package com.ekezet.xrated.parts.home.parts.bottomMenu

import androidx.annotation.IdRes
import com.ekezet.xrated.base.arch.IInteractor
import com.ekezet.xrated.base.arch.IPresenter
import com.ekezet.xrated.base.arch.IView
import com.ekezet.xrated.base.arch.Part
import com.ekezet.xrated.parts.home.parts.bottomMenu.BottomMenuSpec.View

/**
 * @author kiri
 */
interface BottomMenuSpec {
    interface View : IView {
        fun activateMenuItem(@IdRes menuId: Int)

        interface Presenter : IPresenter<View> {
            fun onUserSelectedMenuItem(@IdRes menuId: Int)
        }
    }

    interface Interactor : IInteractor<Nothing> {
        fun emitNavigationEvent(@IdRes menuId: Int)
    }
}

typealias BottomMenuPart = Part<View, View.Presenter, Nothing>
