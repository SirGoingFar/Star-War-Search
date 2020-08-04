package com.trivago.starwarsearch.domain.network.client

import com.trivago.starwarsearch.domain.dto.specie.Specie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface SpecieApi {

    @GET
    suspend fun fetchSpecieDetail(
        @Url url:String
    ):Response<Specie>

}