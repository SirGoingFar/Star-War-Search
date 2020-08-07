package com.trivago.starwarsearch.common.network.util

import com.google.gson.Gson
import com.trivago.starwarsearch.common.core.utils.ERROR_MESSAGE_GENERIC
import com.trivago.starwarsearch.common.network.model.ApiResponse
import okhttp3.ResponseBody

sealed class Result<out T : Any> {

    val fromSuccessfulProcess: Boolean
        get() = this is Success<T>

    class Success<out T : Any>(val data: T) : Result<T>()

    class Error(responseBody: ResponseBody? = null, defaultMsg: String? = ERROR_MESSAGE_GENERIC) :
        Result<Nothing>() {

        val errorMessage = try {
            Gson().fromJson(responseBody?.charStream(), ApiResponse::class.java)?.message
                ?: defaultMsg!!
        } catch (ex: Exception) {
            defaultMsg!!
        }

    }

}