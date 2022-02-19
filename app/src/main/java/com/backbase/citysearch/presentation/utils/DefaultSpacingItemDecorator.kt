package com.backbase.citysearch.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DefaultSpacingItemDecorator(
    private val horizontalSpacing: Int,
    private val verticaSpacing: Int,
    private val showFirstDivider: Boolean = false,
    private val showLastDivider: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val childCount = parent.childCount
        val childPosition = parent.getChildAdapterPosition(view)
        val isLast = childPosition == childCount - 1
        val isFirst = parent.getChildLayoutPosition(view) == 0

        when (getOrientation(parent)) {
            RecyclerView.HORIZONTAL -> {
                if (isFirst) {
                    if (showFirstDivider) {
                        outRect.left = horizontalSpacing
                    }
                } else {
                    outRect.left = horizontalSpacing
                }

                if (isLast) {
                    if (showLastDivider) {
                        outRect.right = horizontalSpacing
                    }
                } else {
                    outRect.right = horizontalSpacing
                }
            }
            RecyclerView.VERTICAL -> {
                if (isFirst) {
                    if (showFirstDivider) {
                        outRect.top = verticaSpacing
                    }
                } else {
                    outRect.top = verticaSpacing
                }

                if (isLast && showLastDivider) {
                    if (showLastDivider) {
                        outRect.bottom = verticaSpacing
                    }
                } else {
                    outRect.bottom = verticaSpacing
                }
            }
        }
    }

    private fun getOrientation(parent: RecyclerView): Int {
        return if (parent.layoutManager is LinearLayoutManager) {
            val layoutManager = parent.layoutManager as LinearLayoutManager?
            layoutManager!!.orientation
        } else {
            throw IllegalStateException(
                "DividerItemDecoration can only be used with a LinearLayoutManager."
            )
        }
    }
}