package com.trivago.starwarsearch.domain.usecase

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.core.interactor.UseCase
import com.trivago.starwarsearch.domain.dto.Character
import com.trivago.starwarsearch.domain.repository.CharacterRepository
import javax.inject.Inject

class SearchCharacter @Inject constructor(
    private val repository: CharacterRepository
) : UseCase<List<Character>, String>() {

    override suspend fun execute(params: String): Either<Failure, List<Character>> {
        return repository.searchCharacterWithTerm(params)
    }

}