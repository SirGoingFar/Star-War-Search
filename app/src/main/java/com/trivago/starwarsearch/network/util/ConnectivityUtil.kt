package com.trivago.starwarsearch.network.util

import android.content.Context
import android.net.ConnectivityManager
import java.io.IOException
import java.net.ConnectException

object ConnectivityUtil {

    fun isConnected(context: Context): Boolean {

        val isConnected: Boolean
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting

        return isConnected
    }

    fun isNoConnectivity(ex: Throwable): Boolean {
        ex.printStackTrace()
        return (ex is NoConnectivityException) || (ex is ConnectException)
    }

}

class NoConnectivityException(message: String?) : IOException(message)