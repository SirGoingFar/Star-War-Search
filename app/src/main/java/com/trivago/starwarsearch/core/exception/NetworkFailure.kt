package com.trivago.starwarsearch.core.exception

sealed class NetworkFailure : Failure() {

    object Connection : Failure()

    class ApiCall(val errorCode: Int, val errorMsg: String) : Failure()

}