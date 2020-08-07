package com.trivago.starwarsearch.domain.usecase.character_search

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.common.core.interactor.UseCase
import com.trivago.starwarsearch.data.dto.character_search.Character
import com.trivago.starwarsearch.data.repository.character.CharacterRepository
import javax.inject.Inject

class SearchCharacter @Inject constructor(
    private val repository: CharacterRepository
) : UseCase<List<Character>, String>() {

    override suspend fun execute(params: String): Either<Failure, List<Character>> {
        return repository.searchCharacterWithTerm(params)
    }

}