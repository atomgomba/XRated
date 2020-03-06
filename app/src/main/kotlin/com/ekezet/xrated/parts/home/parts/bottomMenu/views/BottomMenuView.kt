package com.ekezet.xrated.parts.home.parts.bottomMenu.views

import android.annotation.SuppressLint
import android.content.Context
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.views.BaseView
import com.ekezet.xrated.parts.home.parts.bottomMenu.BottomMenuSpec.View
import com.ekezet.xrated.parts.home.parts.bottomMenu.BottomMenuSpec.View.Presenter
import kotlinx.android.synthetic.main.view_bottom_menu.view.*
import javax.inject.Inject

/**
 * @author kiri
 */
@SuppressLint("ViewConstructor")
@ActivityScope
class BottomMenuView @Inject constructor(
    context: Context,
    private val presenter: Presenter
) : BaseView(context), View {
    override fun setup(idRes: Int) {
        super.setup(idRes)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            presenter.onUserSelectedMenuItem(menuItem.itemId)
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun activateMenuItem(menuId: Int) {
        bottomNavigationView.selectedItemId = menuId
    }
}
