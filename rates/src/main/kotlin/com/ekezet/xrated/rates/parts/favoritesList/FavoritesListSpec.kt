package com.ekezet.xrated.rates.parts.favoritesList

import com.ekezet.xrated.base.arch.Part
import com.ekezet.xrated.rates.base.BaseListSpec
import com.ekezet.xrated.rates.parts.favoritesList.FavoritesListSpec.Interactor
import com.ekezet.xrated.rates.parts.favoritesList.FavoritesListSpec.View

/**
 * @author kiri
 */
interface FavoritesListSpec {
    interface View : BaseListSpec.View {
        interface Presenter : BaseListSpec.View.Presenter<View>
    }

    interface Interactor : BaseListSpec.Interactor<View, Interactor.Presenter> {
        interface Presenter : BaseListSpec.Interactor.Presenter<View>
    }
}

typealias FavoritesListPart = Part<View, View.Presenter, Interactor.Presenter>
