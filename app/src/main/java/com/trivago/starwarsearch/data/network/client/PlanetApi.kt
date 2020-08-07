package com.trivago.starwarsearch.data.network.client

import com.trivago.starwarsearch.data.dto.specie.Planet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PlanetApi {

    @GET
    suspend fun fetchPlanetDetail(
        @Url url: String
    ): Response<Planet>

}