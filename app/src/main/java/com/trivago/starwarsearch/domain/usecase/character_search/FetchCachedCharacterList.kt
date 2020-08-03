package com.trivago.starwarsearch.domain.usecase.character_search

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.core.interactor.UseCase
import com.trivago.starwarsearch.domain.dto.character_search.Character
import com.trivago.starwarsearch.domain.repository.character.CharacterRepository
import javax.inject.Inject

class FetchCachedCharacterList @Inject constructor(
    private val repository: CharacterRepository
) : UseCase<List<Character>, UseCase.None>() {

    override suspend fun execute(params: None): Either<Failure, List<Character>> {
        return repository.fetchCharacterList()
    }

}