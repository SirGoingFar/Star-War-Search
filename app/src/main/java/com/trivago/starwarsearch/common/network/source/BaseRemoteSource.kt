package com.trivago.starwarsearch.common.network.source

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.exception.NetworkFailure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.common.core.utils.ERROR_NETWORK_CALL_NOT_SUCCESSFUL
import com.trivago.starwarsearch.common.network.util.NetworkHandler
import com.trivago.starwarsearch.common.network.util.Result
import com.trivago.starwarsearch.common.network.util.getHttpCode
import com.trivago.starwarsearch.common.network.util.isHttpThrowable
import com.trivago.starwarsearch.common.network.util.parseHttpError
import retrofit2.Response
import javax.inject.Inject


open class BaseRemoteSource @Inject constructor() {

    @Inject
    lateinit var networkHandler: NetworkHandler

    open suspend fun <R> request(call: suspend () -> Response<R>): Either<Failure, R> {

        return try {

            when (networkHandler.isConnected) {

                //There is a network connection, proceed!
                true -> {

                    return try{
                        //API response
                        val response = call.invoke()

                        when (response.isSuccessful) {

                            //API call was successful
                            true -> Either.Right(response.body()!!)

                            //API call was not successful
                            false -> Either.Left(
                                NetworkFailure.ApiCall(
                                    response.code(),
                                    Result.Error(
                                        response.errorBody(),
                                        defaultMsg = ERROR_NETWORK_CALL_NOT_SUCCESSFUL
                                    ).errorMessage
                                )
                            )

                        }
                    }catch (ex:Exception){
                        Either.Left(
                            NetworkFailure.ApiCall(
                                if (ex.isHttpThrowable()) ex.getHttpCode() else 0,
                                parseHttpError(
                                    ERROR_NETWORK_CALL_NOT_SUCCESSFUL
                                )(ex)
                            )
                        )
                    }

                }

                //Network connection is BAD, return a Network Connection Failure
                else -> Either.Left(NetworkFailure.Connection)

            }

        } catch (throwable: Throwable) {
            //Ooops! An error truly occurred
            Either.Left(
                NetworkFailure.ApiCall(
                    if (throwable.isHttpThrowable()) throwable.getHttpCode() else 0,
                    parseHttpError(
        ERROR_NETWORK_CALL_NOT_SUCCESSFUL
    )(throwable)
                )
            )
        }

    }

}