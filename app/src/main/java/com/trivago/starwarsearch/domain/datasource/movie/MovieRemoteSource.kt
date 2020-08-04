package com.trivago.starwarsearch.domain.datasource.movie

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.domain.dto.film.Movie
import com.trivago.starwarsearch.domain.network.AppNetworkService
import com.trivago.starwarsearch.network.source.BaseRemoteSource
import javax.inject.Inject

class MovieRemoteSource @Inject constructor(
    private val networkService: AppNetworkService
) : BaseRemoteSource(), MovieDataSource {

    override suspend fun fetchMovieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMovieDetailByMovieUrl(url: String): Either<Failure, Movie> {
        return request { networkService.movieApi.fetchFilmDetail(url) }
    }

    override suspend fun save(url: String, film: Movie) {
        TODO("Not yet implemented")
    }
}