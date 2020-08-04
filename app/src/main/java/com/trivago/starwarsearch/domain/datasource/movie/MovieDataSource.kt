package com.trivago.starwarsearch.domain.datasource.movie

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.film.Movie

interface MovieDataSource {

    suspend fun fetchMovieListByCharacterUrl(url: String): Either<Failure, List<String>>

    suspend fun fetchMovieDetailByMovieUrl(url: String): Either<Failure, Movie>

    suspend fun save(url: String, film: Movie)

}