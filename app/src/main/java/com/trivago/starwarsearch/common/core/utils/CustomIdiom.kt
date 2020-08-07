package com.trivago.starwarsearch.common.core.utils

import android.os.Handler
import android.os.Looper

fun runBackground(operation: () -> Unit): Thread {
    return Thread {
        operation.invoke()
    }
}

fun runOnMainThread(operation: () -> Unit) {
    Handler(Looper.getMainLooper()).post {
        operation.invoke()
    }
}