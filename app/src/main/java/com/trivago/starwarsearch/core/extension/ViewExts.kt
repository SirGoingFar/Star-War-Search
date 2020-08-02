package com.trivago.starwarsearch.core.extension

import android.view.View

fun View.show(show: Boolean, onlyInvisible: Boolean = false) {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        if (onlyInvisible)
            this.visibility = View.INVISIBLE
        else
            this.visibility = View.GONE
    }
}