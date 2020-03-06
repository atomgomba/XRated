package com.ekezet.xrated.parts.home.views

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekezet.xrated.base.arch.AnyPart
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.parts.home.views.HomePagerAdapter.VH
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class HomePagerAdapter @Inject constructor(
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<VH>() {
    private val pages: MutableList<AnyPart> = ArrayList()

    fun setPages(newPages: List<AnyPart>) {
        pages.clear()
        pages.addAll(newPages)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(FrameLayout(parent.context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        })

    override fun getItemCount() = pages.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        pages[position].bootWithContainer(holder.itemView as ViewGroup, lifecycleOwner.lifecycle)
    }

    class VH(itemView: View) : ViewHolder(itemView)
}
