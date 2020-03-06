package com.ekezet.xrated.parts.home.parts.bottomMenu

import com.ekezet.xrated.base.BaseInteractor
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.di.MENU
import com.ekezet.xrated.parts.home.di.NavigationChannel
import com.ekezet.xrated.parts.home.parts.bottomMenu.BottomMenuSpec.Interactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * @author kiri
 */
@ActivityScope
class BottomMenuInteractor @Inject constructor(
    @Named(MENU) private val navigationChannel: NavigationChannel
) : BaseInteractor<Nothing>(), Interactor {
    override val coroutineContext = Dispatchers.Main

    override fun emitNavigationEvent(menuId: Int) {
        launch {
            navigationChannel.send(menuId)
        }
    }
}
