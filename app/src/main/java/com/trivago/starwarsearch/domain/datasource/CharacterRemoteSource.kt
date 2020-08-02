package com.trivago.starwarsearch.domain.datasource

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.Character
import com.trivago.starwarsearch.domain.dto.SearchCharacterResponse
import com.trivago.starwarsearch.domain.network.CharacterNetworkService
import com.trivago.starwarsearch.network.source.BaseRemoteSource
import javax.inject.Inject

class CharacterRemoteSource @Inject constructor(
    private val networkService: CharacterNetworkService
) : BaseRemoteSource(), CharacterDataSource {

    override suspend fun searchCharacter(term: String, page:Int): Either<Failure, SearchCharacterResponse> {
        return request {
            networkService.characterApi.searchCharacter(term, page)
        }
    }

    override suspend fun fetchCharacterByUrl(url: String): Either<Failure, Character> {
        TODO("Not needed for remote source")
    }

    override suspend fun fetchCachedCharacterList(): Either<Failure, List<Character>> {
        TODO("Not needed for remote source")
    }

    override suspend fun save(list: List<Character>) {
        TODO("Not needed for remote source")
    }

    override suspend fun deleteAll() {
        TODO("Not needed for remote source")
    }
}