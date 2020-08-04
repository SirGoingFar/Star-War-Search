package com.trivago.starwarsearch.domain.usecase.movie

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.core.interactor.UseCase
import com.trivago.starwarsearch.domain.dto.film.Movie
import com.trivago.starwarsearch.domain.repository.movie.MovieRepository
import javax.inject.Inject

class FetchMovieDetailByUrl @Inject constructor(private val repository: MovieRepository) :
    UseCase<Movie, String>() {
    override suspend fun execute(params: String): Either<Failure, Movie> {
        return repository.fetchMovieDetailByMovieUrl(params)
    }
}