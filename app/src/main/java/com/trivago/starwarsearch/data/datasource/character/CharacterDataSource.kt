package com.trivago.starwarsearch.data.datasource.character

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.character_search.SearchCharacterResponse
import com.trivago.starwarsearch.data.dto.character_search.Character

interface CharacterDataSource {

    suspend fun searchCharacter(term: String, page:Int): Either<Failure, SearchCharacterResponse>

    suspend fun fetchCharacterByUrl(url: String): Either<Failure, Character>

    suspend fun fetchCharacterList(): Either<Failure, List<Character>>

    suspend fun fetchFilmListByCharacterUrl(url: String): Either<Failure, List<String>>

    suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>>

    suspend fun save(list: List<Character>)

    suspend fun deleteAll()

}