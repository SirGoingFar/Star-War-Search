package com.trivago.starwarsearch.data.network.client

import com.trivago.starwarsearch.data.dto.film.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MovieApi {

    @GET
    suspend fun fetchFilmDetail(
        @Url url: String
    ): Response<Movie>

}