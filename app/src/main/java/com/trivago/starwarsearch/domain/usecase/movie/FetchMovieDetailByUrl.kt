package com.trivago.starwarsearch.domain.usecase.movie

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.common.core.interactor.UseCase
import com.trivago.starwarsearch.data.dto.film.Movie
import com.trivago.starwarsearch.data.repository.movie.MovieRepository
import javax.inject.Inject

class FetchMovieDetailByUrl @Inject constructor(private val repository: MovieRepository) :
    UseCase<Movie, String>() {
    override suspend fun execute(params: String): Either<Failure, Movie> {
        return repository.fetchMovieDetailByMovieUrl(params)
    }
}