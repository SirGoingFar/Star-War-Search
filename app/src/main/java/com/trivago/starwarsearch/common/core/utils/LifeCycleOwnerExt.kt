package com.trivago.starwarsearch.common.core.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(this, observer)
}