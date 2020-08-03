package com.trivago.starwarsearch.domain.datasource.character

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.character_search.Character
import com.trivago.starwarsearch.domain.dto.character_search.SearchCharacterResponse
import com.trivago.starwarsearch.domain.network.AppNetworkService
import com.trivago.starwarsearch.network.source.BaseRemoteSource
import javax.inject.Inject

class CharacterRemoteSource @Inject constructor(
    private val networkService: AppNetworkService
) : BaseRemoteSource(),
    CharacterDataSource {

    override suspend fun searchCharacter(
        term: String,
        page: Int
    ): Either<Failure, SearchCharacterResponse> {
        return request {
            networkService.characterApi.searchCharacter(term, page)
        }
    }

    override suspend fun fetchFilmListByCharacterUrl(url: String): Either<Failure, List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCharacterByUrl(url: String): Either<Failure, Character> {
        TODO("Not needed for remote source")
    }

    override suspend fun fetchCharacterList(): Either<Failure, List<Character>> {
        TODO("Not needed for remote source")
    }

    override suspend fun save(list: List<Character>) {
        TODO("Not needed for remote source")
    }

    override suspend fun deleteAll() {
        TODO("Not needed for remote source")
    }
}