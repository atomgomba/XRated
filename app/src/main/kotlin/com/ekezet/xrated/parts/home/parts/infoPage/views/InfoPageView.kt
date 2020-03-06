package com.ekezet.xrated.parts.home.parts.infoPage.views

import android.content.Context
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import com.ekezet.xrated.R
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.views.BaseView
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPageSpec.View
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPageSpec.View.Presenter
import kotlinx.android.synthetic.main.view_info_page.view.*
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class InfoPageView @Inject constructor(
    context: Context,
    private val presenter: Presenter
) : BaseView(context), View {
    override var publishedAt: CharSequence
        get() = TODO("not implemented")
        set(value) {
            publishedAtText.text = value
        }
    override var downloadedAt: CharSequence
        get() = TODO("not implemented")
        set(value) {
            downloadedAtText.text = value
        }
    override var version: CharSequence
        get() = TODO("not implemented")
        set(value) {
            versionText.text = value
        }
    override var buildId: CharSequence
        get() = TODO("not implemented")
        set(value) {
            buildIdText.text = value
        }
    override var updateHour: Int
        get() = TODO("not implemented")
        set(value) {
            updatesNoteText.text =
                Html.fromHtml(context.getString(R.string.home__info__updates_note, value), FROM_HTML_MODE_LEGACY)
        }

    override fun setup(idRes: Int) {
        super.setup(idRes)
        clearCacheButton.setOnClickListener {
            presenter.onClearCacheButtonClicked()
        }
    }
}
