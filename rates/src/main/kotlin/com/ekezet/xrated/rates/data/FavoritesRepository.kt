package com.ekezet.xrated.rates.data

import com.ekezet.xrated.base.utils.PrefsManager
import com.ekezet.xrated.base.utils.PrefsManager.Companion.PREF_FAVORITES
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.util.SortedSet
import java.util.TreeSet
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author kiri
 */
@Singleton
class FavoritesRepository @Inject constructor(
    private val prefsManager: PrefsManager
) : CoroutineScope {
    override val coroutineContext = Dispatchers.Main

    val favoritesChannel = ConflatedBroadcastChannel<SortedSet<String>>()

    private var favorites: SortedSet<String> = TreeSet()

    init {
        launch {
            // first initialization of favorites list
            onFavoritesChanged()
            prefsManager.changesChannel.consumeEach {
                if (it == PREF_FAVORITES) {
                    onFavoritesChanged()
                }
            }
        }
    }

    fun addFavorite(currencyCode: String) {
        if (favorites.add(currencyCode)) {
            prefsManager.favorites = favorites
        }
    }

    fun removeFavorites(currencyCode: String) {
        if (favorites.remove(currencyCode)) {
            prefsManager.favorites = favorites
        }
    }

    fun isFavorite(currencyCode: String) = favorites.contains(currencyCode)

    private suspend fun onFavoritesChanged() {
        favorites.clear()
        favorites.addAll(prefsManager.favorites)
        favoritesChannel.send(favorites)
    }
}
