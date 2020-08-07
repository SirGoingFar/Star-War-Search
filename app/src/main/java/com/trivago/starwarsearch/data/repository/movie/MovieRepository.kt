package com.trivago.starwarsearch.data.repository.movie

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.film.Movie

interface MovieRepository {
    suspend fun fetchMovieListByCharacterUrl(url:String): Either<Failure, List<String>>
    suspend fun fetchMovieDetailByMovieUrl(url: String):Either<Failure,Movie>
}