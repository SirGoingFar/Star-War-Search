package com.trivago.starwarsearch.domain.usecase.character_search

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.common.core.interactor.UseCase
import com.trivago.starwarsearch.data.dto.character_search.Character
import com.trivago.starwarsearch.data.repository.character.CharacterRepository
import javax.inject.Inject

class FetchCharacterByUrl @Inject constructor(
    private val repository: CharacterRepository
) : UseCase<Character, String>() {
    override suspend fun execute(params: String): Either<Failure, Character> {
        return repository.fetchCharacterByUrl(params)
    }

}