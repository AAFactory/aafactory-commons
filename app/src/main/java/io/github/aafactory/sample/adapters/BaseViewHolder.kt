package io.github.aafactory.sample.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by CHO HANJOONG on 2018-04-20.
 */

abstract class BaseViewHolder<T : Any>(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    abstract fun bindData(data: T)
}