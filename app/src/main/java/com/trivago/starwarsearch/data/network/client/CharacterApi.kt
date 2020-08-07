package com.trivago.starwarsearch.data.network.client

import com.trivago.starwarsearch.data.dto.character_search.SearchCharacterResponse
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