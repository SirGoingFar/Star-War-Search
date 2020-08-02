package com.trivago.starwarsearch.views.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addInfiniteScrollListener(
    visibleItemThreshold: Int = 3,
    action: (() -> Unit)?
) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (isXPositionItemDisplaying(recyclerView, visibleItemThreshold)) {
                action?.invoke()
            }
        }
    })
}

private fun isXPositionItemDisplaying(
    recyclerView: RecyclerView,
    visibleItemThreshold: Int
): Boolean {
    val rvItemCount = recyclerView.adapter!!.itemCount
    if (rvItemCount != 0) {

        val lastVisibleItemPosition =
            (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()

        //ensure the least visible threshold is >= 1
        val threshold = if (visibleItemThreshold < 1) 1 else visibleItemThreshold

        if (lastVisibleItemPosition != RecyclerView.NO_POSITION
            && lastVisibleItemPosition == rvItemCount - threshold
        ) return true
    }

    return false
}