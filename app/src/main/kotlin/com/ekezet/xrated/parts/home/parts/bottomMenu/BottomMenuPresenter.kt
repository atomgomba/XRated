package com.ekezet.xrated.parts.home.parts.bottomMenu

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.R
import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.parts.home.parts.bottomMenu.BottomMenuSpec.Interactor
import com.ekezet.xrated.parts.home.parts.bottomMenu.BottomMenuSpec.View
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class BottomMenuPresenter @Inject constructor() : BasePresenter<View, Interactor, Nothing>(), View.Presenter {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        view.setup(R.layout.view_bottom_menu)
    }

    override fun onUserSelectedMenuItem(menuId: Int) {
        interactor!!.emitNavigationEvent(menuId)
    }
}
