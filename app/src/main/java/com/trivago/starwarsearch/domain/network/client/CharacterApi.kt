package com.trivago.starwarsearch.domain.network.client

import com.trivago.starwarsearch.domain.dto.character_search.SearchCharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("api/people")
    suspend fun searchCharacter(
        @Query("search") term: String,
        @Query("page") page: Int
    ): Response<SearchCharacterResponse>

}