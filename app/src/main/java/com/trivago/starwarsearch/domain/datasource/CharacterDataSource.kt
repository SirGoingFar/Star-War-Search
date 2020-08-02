package com.trivago.starwarsearch.domain.datasource

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.SearchCharacterResponse
import com.trivago.starwarsearch.domain.dto.Character

interface CharacterDataSource {

    suspend fun searchCharacter(term: String, page:Int): Either<Failure, SearchCharacterResponse>

    suspend fun fetchCharacterByUrl(url: String): Either<Failure, Character>

    suspend fun fetchCachedCharacterList(): Either<Failure, List<Character>>

    suspend fun save(list: List<Character>)

    suspend fun deleteAll()

}