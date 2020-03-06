package com.ekezet.xrated.parts.home.di

import com.ekezet.xrated.R
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.di.MENU
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPagePart
import com.ekezet.xrated.parts.home.parts.infoPage.di.InfoPageModule
import com.ekezet.xrated.rates.parts.favoritesList.FavoritesListPart
import com.ekezet.xrated.rates.parts.favoritesList.di.FavoritesListModule
import com.ekezet.xrated.rates.parts.ratesList.RatesListPart
import com.ekezet.xrated.rates.parts.ratesList.di.RatesListModule
import com.ekezet.xrated.viewmodels.NavigationItem
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * @author kiri
 */
@Module(
    includes = [
        RatesListModule::class,
        FavoritesListModule::class,
        InfoPageModule::class
    ]
)
class NavigationModule {
    @Provides @Named(MENU) @ActivityScope fun provideMenuItems(
        ratesListPart: RatesListPart,
        favoritesListPart: FavoritesListPart,
        infoPagePart: InfoPagePart
    ): List<NavigationItem> =
        listOf(
            NavigationItem(R.id.navDailyRates, ratesListPart),
            NavigationItem(R.id.navFavorites, favoritesListPart),
            NavigationItem(R.id.navInfo, infoPagePart)
        )
}
