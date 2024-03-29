package io.github.aafactory.sample.helpers

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.aafactory.commons.extensions.dpToPixel

class ItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = context.dpToPixel(3F)
    }
}