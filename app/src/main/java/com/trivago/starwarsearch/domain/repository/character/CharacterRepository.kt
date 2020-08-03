package com.trivago.starwarsearch.domain.repository.character

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.character_search.Character

interface CharacterRepository {

    suspend fun searchCharacterWithTerm(term: String): Either<Failure, List<Character>>

    suspend fun fetchCharacterByUrl(cardId: String): Either<Failure, Character>

    suspend fun fetchCharacterList(): Either<Failure, List<Character>>

    suspend fun fetchFilmListByCharacterUrl(url: String): Either<Failure, List<String>>

    suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>>

}