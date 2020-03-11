package com.ekezet.xrated.rates.di

import android.content.Context
import com.ekezet.xrated.base.data.cache.BareCachedItem
import com.ekezet.xrated.base.data.cache.CacheStore
import com.ekezet.xrated.base.data.cache.CachedItem
import com.ekezet.xrated.base.data.cache.expirationStrategies.TtlExpirationStrategy
import com.ekezet.xrated.base.data.cache.layers.DiskCacheLayer
import com.ekezet.xrated.base.data.cache.layers.MemoryCacheLayer
import com.ekezet.xrated.base.di.APP
import com.ekezet.xrated.rates.R
import com.ekezet.xrated.rates.api.ExchangeRatesApiInterface
import com.ekezet.xrated.rates.data.cache.RatesCacheKey
import com.ekezet.xrated.rates.data.cache.WeekendExpirationStrategy
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author kiri
 */
@Module
object RatesModule {
    @Provides @Singleton fun provideRatesRetrofitApiInterface(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): ExchangeRatesApiInterface =
        Retrofit.Builder()
            .baseUrl("https://api.exchangeratesapi.io/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(ExchangeRatesApiInterface::class.java)

    @Provides @Named(UPDATE_HOUR) @Singleton fun provideUpdateHour(@Named(APP) context: Context) =
        context.resources.getInteger(R.integer.update_hour)

    @Provides @Named(NUM_OF_CURRENCIES) @Singleton fun provideNumOfCurrencies(@Named(APP) context: Context) =
        context.resources.getInteger(R.integer.number_of_currencies)

    @Provides @Singleton fun provideRatesCacheStore(
        @Named(APP) context: Context,
        moshi: Moshi,
        @Named(NUM_OF_CURRENCIES) capacity: Int
    ): ExchangeRatesStore {
        val cachedItemType = Types.newParameterizedType(
            CachedItem::class.java,
            RatesCacheKey::class.java,
            Types.newParameterizedType(
                Map::class.java,
                String::class.java,
                CachedExchangeRates::class.java
            )
        )
        val journalItemType = Types.newParameterizedType(BareCachedItem::class.java, RatesCacheKey::class.java)
        val journalType = Types.newParameterizedType(MutableMap::class.java, String::class.java, journalItemType)
        return CacheStore.Builder<RatesCacheKey, ExchangeRatesMap>()
            .capacity(capacity)
            .expirationStrategy(WeekendExpirationStrategy())
            .expirationStrategy(TtlExpirationStrategy(60 * 60* 1000))
            .layer(MemoryCacheLayer(capacity))
            .layer(
                DiskCacheLayer(
                    File(
                        context.filesDir,
                        "store/exchange-rates"
                    ),
                    moshi.adapter(cachedItemType),
                    moshi.adapter(journalType)
                )
            )
            .build()
    }
}
