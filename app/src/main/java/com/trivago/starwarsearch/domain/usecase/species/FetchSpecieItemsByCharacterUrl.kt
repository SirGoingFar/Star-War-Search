package com.trivago.starwarsearch.domain.usecase.species

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.core.interactor.UseCase
import com.trivago.starwarsearch.domain.dto.common.ListItem
import com.trivago.starwarsearch.domain.repository.specie.SpecieRepository
import javax.inject.Inject

class FetchSpecieItemsByCharacterUrl @Inject constructor(
    private val repository: SpecieRepository
) : UseCase<List<ListItem>, String>() {

    override suspend fun execute(params: String): Either<Failure, List<ListItem>> {
        return Either.Right(
            (repository.fetchSpecieListByCharacterUrl(params) as Either.Right).b.mapIndexed { index, url ->
                ListItem(url, "Specie ".plus(index + 1))
            }
        )
    }
}