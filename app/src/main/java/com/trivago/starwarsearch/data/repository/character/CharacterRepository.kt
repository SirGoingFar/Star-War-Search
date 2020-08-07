package com.trivago.starwarsearch.data.repository.character

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.character_search.Character

interface CharacterRepository {

    suspend fun searchCharacterWithTerm(term: String): Either<Failure, List<Character>>

    suspend fun fetchCharacterByUrl(characterUrl: String): Either<Failure, Character>

    suspend fun fetchCharacterList(): Either<Failure, List<Character>>

    suspend fun fetchFilmListByCharacterUrl(url: String): Either<Failure, List<String>>

    suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>>

}