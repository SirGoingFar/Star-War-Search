package com.trivago.starwarsearch.domain.repository

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.Character

interface CharacterRepository {

    suspend fun searchCharacterWithTerm(term: String): Either<Failure, List<Character>>

    suspend fun fetchCharacterByUrl(cardId: String): Either<Failure, Character>

    suspend fun fetchCachedCharacterList(): Either<Failure, List<Character>>

}