package com.ekezet.xrated.base.parts.itemPicker.di

import android.content.Context
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerPart
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerPresenter
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerRouter
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerSpec.Router
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerSpec.View
import com.ekezet.xrated.base.parts.itemPicker.di.ItemPickerActivityModule.Binder
import com.ekezet.xrated.base.parts.itemPicker.views.ItemPickerActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * @author kiri
 */
@Module(
    includes = [
        Binder::class
    ]
)
class ItemPickerActivityModule {
    @Module
    abstract class Binder {
        @Binds @ActivityScope abstract fun bindContext(activity: ItemPickerActivity): Context
        @Binds @ActivityScope abstract fun bindView(activity: ItemPickerActivity): View
        @Binds @ActivityScope abstract fun bindPresenter(presenter: ItemPickerPresenter): View.Presenter
        @Binds @ActivityScope abstract fun bindRouter(router: ItemPickerRouter): Router
    }

    @Provides @ActivityScope fun providePart(
        view: View,
        presenter: View.Presenter,
        router: Router
    ) =
        ItemPickerPart(view, null, presenter, router)
}
