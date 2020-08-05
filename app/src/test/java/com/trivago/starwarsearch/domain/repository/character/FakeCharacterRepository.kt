package com.trivago.starwarsearch.domain.repository.character

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.datasource.character.CharacterDataSource
import com.trivago.starwarsearch.domain.dto.character_search.Character

class FakeCharacterRepository(
    private val localSource: CharacterDataSource,
    private val remoteSource: CharacterDataSource
) : CharacterRepository {

    override suspend fun searchCharacterWithTerm(term: String): Either<Failure, List<Character>> {
        val resp = remoteSource.searchCharacter(term, 1)
        val characterList = (resp as Either.Right).b.list
        localSource.save(characterList)
        return Either.Right(characterList)
    }

    override suspend fun fetchCharacterByUrl(characterUrl: String): Either<Failure, Character> {
        return localSource.fetchCharacterByUrl(characterUrl)
    }

    override suspend fun fetchCharacterList(): Either<Failure, List<Character>> {
        return localSource.fetchCharacterList()
    }

    override suspend fun fetchFilmListByCharacterUrl(url: String): Either<Failure, List<String>> {
        return localSource.fetchFilmListByCharacterUrl(url)
    }

    override suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        return localSource.fetchSpecieListByCharacterUrl(url)
    }

}