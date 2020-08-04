package com.trivago.starwarsearch.domain.usecase.movie

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.core.interactor.UseCase
import com.trivago.starwarsearch.domain.dto.common.ListItem
import com.trivago.starwarsearch.domain.repository.movie.MovieRepository
import javax.inject.Inject

class FetchMovieItemsByCharacterUrl @Inject constructor(
    private val repository: MovieRepository
) : UseCase<List<ListItem>, String>() {

    override suspend fun execute(params: String): Either<Failure, List<ListItem>> {
        return Either.Right(
            (repository.fetchMovieListByCharacterUrl(params) as Either.Right).b.mapIndexed { index, url ->
                ListItem(url, "Movie ".plus(index + 1))
            }
        )
    }
}