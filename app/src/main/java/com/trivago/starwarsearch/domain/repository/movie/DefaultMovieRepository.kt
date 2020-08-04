package com.trivago.starwarsearch.domain.repository.movie

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.di.annotation.movie.MovieLocalDataSourceQualifier
import com.trivago.starwarsearch.di.annotation.movie.MovieRemoteDataSourceQualifier
import com.trivago.starwarsearch.domain.datasource.movie.MovieDataSource
import com.trivago.starwarsearch.domain.dto.film.Movie
import javax.inject.Inject

class DefaultMovieRepository @Inject constructor(
    @MovieLocalDataSourceQualifier private val localSource: MovieDataSource,
    @MovieRemoteDataSourceQualifier private val remoteSource: MovieDataSource
) : MovieRepository {

    override suspend fun fetchMovieListByCharacterUrl(url: String): Either<Failure, List<String>> {
        return localSource.fetchMovieListByCharacterUrl(url)
    }

    override suspend fun fetchMovieDetailByMovieUrl(url: String): Either<Failure, Movie> {
        //check if the detail is available in process light cache
        val cacheVersionResp = localSource.fetchMovieDetailByMovieUrl(url)
        if (cacheVersionResp.isRight)
            return cacheVersionResp

        //if not, make a network call
        val response = remoteSource.fetchMovieDetailByMovieUrl(url)
        if (response.isRight)
            localSource.save(url, (response as Either.Right).b)

        return response
    }

}