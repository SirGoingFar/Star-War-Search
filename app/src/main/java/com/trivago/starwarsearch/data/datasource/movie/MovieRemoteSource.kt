package com.trivago.starwarsearch.data.datasource.movie

import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.functional.Either
import com.trivago.starwarsearch.data.dto.film.Movie
import com.trivago.starwarsearch.data.network.client.AppNetworkService
import com.trivago.starwarsearch.common.network.source.BaseRemoteSource
import javax.inject.Inject

class MovieRemoteSource @Inject constructor(
    private val networkService: AppNetworkService
) : BaseRemoteSource(), MovieDataSource {

    override suspend fun fetchMovieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        TODO("Not necessary for remote data source")
    }

    override suspend fun fetchMovieDetailByMovieUrl(url: String): Either<Failure, Movie> {
        return request { networkService.getMovieApi().fetchFilmDetail(url) }
    }

    override suspend fun save(url: String, film: Movie) {
        TODO("Not necessary for remote data source")
    }
}