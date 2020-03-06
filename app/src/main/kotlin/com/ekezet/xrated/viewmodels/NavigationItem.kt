package com.ekezet.xrated.viewmodels

import androidx.annotation.IdRes
import com.ekezet.xrated.base.arch.AnyPart

/**
 * @author kiri
 */
data class NavigationItem(
    @IdRes val menuId: Int,
    val part: AnyPart
)
