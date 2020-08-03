package com.trivago.starwarsearch.domain.network.client

import com.trivago.starwarsearch.domain.dto.film.Film
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface FilmApi {

    @GET
    suspend fun fetchFilmDetail(
        @Url url: String
    ): Response<Film>

}