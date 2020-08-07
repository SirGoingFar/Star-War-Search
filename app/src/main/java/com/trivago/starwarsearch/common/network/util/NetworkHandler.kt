package com.trivago.starwarsearch.common.network.util

import android.content.Context
import com.trivago.starwarsearch.common.core.extension.networkInfo
import javax.inject.Inject

/**
 * Injectable class which returns information about the network connection state.
 */
class NetworkHandler @Inject constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}