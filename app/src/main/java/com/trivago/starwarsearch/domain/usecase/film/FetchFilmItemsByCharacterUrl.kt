package com.trivago.starwarsearch.domain.usecase.film

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.core.interactor.UseCase
import com.trivago.starwarsearch.domain.dto.common.ListItem
import com.trivago.starwarsearch.domain.repository.film.FilmRepository
import javax.inject.Inject

class FetchFilmItemsByCharacterUrl @Inject constructor(
    private val repository: FilmRepository
) : UseCase<List<ListItem>, String>() {

    override suspend fun execute(params: String): Either<Failure, List<ListItem>> {
        return Either.Right(
            (repository.fetchFilmListByCharacterUrl(params) as Either.Right).b.mapIndexed { index, url ->
                ListItem(url, "Film ".plus(index + 1))
            }
        )
    }
}