package com.trivago.starwarsearch.domain.datasource.character

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.character_search.Character
import com.trivago.starwarsearch.domain.dto.character_search.SearchCharacterResponse
import javax.inject.Inject

class CharacterLocalSource @Inject constructor() :
    CharacterDataSource {

    private val characterList: ArrayList<Character> = ArrayList()

    override suspend fun searchCharacter(
        term: String,
        page: Int
    ): Either<Failure, SearchCharacterResponse> {
        TODO("Not necessary for local data source")
    }

    override suspend fun fetchCharacterByUrl(url: String): Either<Failure, Character> {
        val character: Character? = characterList.find { it.url == url }
        return if (character == null)
            Either.Left(ItemNotFoundException)
        else
            Either.Right(character)
    }

    override suspend fun fetchCharacterList(): Either<Failure, List<Character>> {
        return Either.Right(characterList)
    }

    override suspend fun fetchFilmListByCharacterUrl(url: String): Either<Failure, List<String>> {
        val character: Character? = characterList.find { it.url == url }
        return if (character == null)
            Either.Left(ItemNotFoundException)
        else
            Either.Right(character.films)
    }

    override suspend fun fetchSpecieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        val character: Character? = characterList.find { it.url == url }
        return if (character == null)
            Either.Left(ItemNotFoundException)
        else
            Either.Right(character.species)
    }

    override suspend fun save(list: List<Character>) {
        characterList.addAll(list.toMutableList())
    }

    override suspend fun deleteAll() {
        characterList.clear()
    }

}