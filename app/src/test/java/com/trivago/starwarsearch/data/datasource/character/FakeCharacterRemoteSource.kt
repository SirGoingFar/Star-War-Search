package com.trivago.starwarsearch.data.datasource.character

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.character_search.Character
import com.trivago.starwarsearch.data.dto.character_search.SearchCharacterResponse

class FakeCharacterRemoteSource(
    private val response: SearchCharacterResponse
) : CharacterDataSource {

    override suspend fun searchCharacter(
        term: String,
        page: Int
    ): Either<Failure, SearchCharacterResponse> {
        return Either.Right(response)
    }

    override suspend fun fetchCharacterByUrl(url: String): Either<Failure, Character> {
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

    override suspend fun save(list: List<Character>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}