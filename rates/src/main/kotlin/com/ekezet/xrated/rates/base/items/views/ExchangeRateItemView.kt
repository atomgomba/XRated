package com.ekezet.xrated.rates.base.items.views

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.ekezet.xrated.rates.R
import com.ekezet.xrated.rates.base.items.ExchangeRateListItemSpec.View
import kotlinx.android.synthetic.main.list_item_exchange_rate.view.*

/**
 * @author kiri
 */
class ExchangeRateItemView(context: Context) : ConstraintLayout(context), View {
    private val animDuration = context.resources.getInteger(R.integer.favorite_button_anim_duration)
    private val favStateAnimator = AnimatorInflater.loadStateListAnimator(context, R.animator.favorite_button_state)

    init {
        inflate(context, R.layout.list_item_exchange_rate, this)
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    override var currencyCode: CharSequence
        get() = currencyCodeText.text
        set(value) {
            currencyCodeText.text = value
        }

    override var value: CharSequence
        get() = valueText.text
        set(value) {
            valueText.text = value
        }

    override var flag: String
        get() = throw IllegalAccessException("This field cannot be read")
        set(value) {
            @SuppressLint("DefaultLocale")
            val currencyResId = context.resources.getIdentifier(
                "currency_flag_${value.toLowerCase()}",
                "drawable",
                context.applicationContext.packageName
            )
            currencyFlagImage.setImageResource(currencyResId)
        }

    override var isFavorite: Boolean
        get() = favoriteButton.isSelected
        set(value) {
            favoriteButton.isSelected = value
        }

    override fun setContentDescriptions(copyDesc: CharSequence, favoriteDesc: CharSequence) {
        copyButton.contentDescription = copyDesc
        favoriteButton.contentDescription = favoriteDesc
    }

    fun setOnFavoriteButtonClickListener(listener: (android.view.View) -> Unit) {
        favoriteButton.setOnClickListener { v ->
            favoriteButton.stateListAnimator = favStateAnimator
            isFavorite = !isFavorite
            postDelayed({
                favoriteButton.stateListAnimator = null
                listener(v)
            }, animDuration * 2L)
        }
    }

    fun setOnCopyButtonClickListener(listener: (android.view.View) -> Unit) {
        copyButton.setOnClickListener(listener)
    }

    fun setOnLongPressListener(listener: (android.view.View) -> Boolean) {
        setOnLongClickListener(listener)
    }
}
