package com.trivago.starwarsearch.domain.repository.character

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.character_search.Character
import org.junit.Assert.*

class FakeCharacterRepositoryTest:CharacterRepository{
    override suspend fun searchCharacterWithTerm(term: String): Either<Failure, List<Character>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCharacterByUrl(cardId: String): Either<Failure, Character> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCharacterList(): Either<Failure, List<Character>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchFilmListByCharacterUrl(url: String): Either<Failure, List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        TODO("Not yet implemented")
    }
}