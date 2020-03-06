package com.ekezet.xrated.base

import android.content.Context
import com.ekezet.xrated.base.arch.IRouter

/**
 * @author kiri
 */
open class BaseRouter : IRouter {
    protected lateinit var context: Context
        private set

    override fun onBoot(context: Context) {
        this.context = context
    }
}
