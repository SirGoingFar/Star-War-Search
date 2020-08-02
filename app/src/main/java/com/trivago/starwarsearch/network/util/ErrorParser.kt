package com.trivago.starwarsearch.network.util

import com.google.gson.Gson
import com.trivago.starwarsearch.core.utils.ERROR_MESSAGE_BAD_INTERNET_CONNECTION
import com.trivago.starwarsearch.core.utils.ERROR_MESSAGE_GENERIC
import retrofit2.HttpException

/**
 *
 *
 * Helper function parse http exceptions make from retrofit request.
 *
 *
 */
fun parseHttpError(
    genericResponse: String = ERROR_MESSAGE_GENERIC,
    serverErrorMessage: String? = null
): (Throwable) -> String {

    return { throwable ->

        when {

            ConnectivityUtil.isNoConnectivity(throwable) -> ERROR_MESSAGE_BAD_INTERNET_CONNECTION

            throwable is HttpException -> {

                if (throwable.code() in 500..504) {
                    serverErrorMessage ?: ERROR_MESSAGE_GENERIC
                } else {
                    try {
                        val responseMap = Gson().fromJson(
                            throwable.response()!!.errorBody()!!.string(),
                            HashMap::class.java
                        ) as HashMap<*, *>
                        (responseMap["message"] as String)
                    } catch (e: Exception) {
                        genericResponse
                    }
                }
            }

            else -> genericResponse
        }
    }
}

fun Throwable.isHttpThrowable(): Boolean = this is HttpException

/**
 * This method is to further abstract Retrofit from the app module.
 * This attempts to return the Http code in cases of exception
 * @param throwable
 * @return http code
 */
fun Throwable.getHttpCode(): Int =
    when (this) {
        is HttpException -> this.code()
        else -> 0 //not an HttpException
    }