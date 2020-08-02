package com.trivago.starwarsearch.core.extension

import kotlinx.coroutines.Job

fun Job.cancelIfActive() {
    this.let {
        if (it.isActive)
            it.cancel()
    }
}