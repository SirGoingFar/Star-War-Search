package com.trivago.starwarsearch.domain.datasource

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.Character
import com.trivago.starwarsearch.domain.dto.SearchCharacterResponse
import javax.inject.Inject

class CharacterLocalSource @Inject constructor() : CharacterDataSource {

    private val characterList: ArrayList<Character> = ArrayList()

    override suspend fun searchCharacter(term: String, page:Int): Either<Failure, SearchCharacterResponse> {
        TODO("Not necessary for local data source")
    }

    override suspend fun fetchCharacterByUrl(url: String): Either<Failure, Character> {
        val character: Character? = characterList.find { it.url == url }
        return if (character == null)
            Either.Left(ItemNotFoundException)
        else
            Either.Right(character)
    }

    override suspend fun fetchCachedCharacterList(): Either<Failure, List<Character>> {
        return Either.Right(characterList)
    }

    override suspend fun save(list: List<Character>) {
        characterList.addAll(list.toMutableList())
    }

    override suspend fun deleteAll() {
        characterList.clear()
    }

}