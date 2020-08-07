package com.trivago.starwarsearch.data.datasource.movie

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.exception.ItemNotFoundException
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.film.Movie

class FakeMovieRemoteSource(private val movie: Movie) : MovieDataSource {
    override suspend fun fetchMovieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMovieDetailByMovieUrl(url: String): Either<Failure, Movie> {
        return if (url.isEmpty())
            Either.Left(ItemNotFoundException)
        else
            Either.Right(movie)
    }

    override suspend fun save(url: String, film: Movie) {
        TODO("Not yet implemented")
    }
}