package com.trivago.starwarsearch.domain.repository.film

import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.functional.Either
import com.trivago.starwarsearch.di.annotation.film.FilmLocalDataSourceQualifier
import com.trivago.starwarsearch.di.annotation.film.FilmRemoteDataSourceQualifier
import com.trivago.starwarsearch.domain.datasource.film.FilmDataSource
import com.trivago.starwarsearch.domain.dto.film.Film
import javax.inject.Inject

class DefaultFilmRepository @Inject constructor(
    @FilmLocalDataSourceQualifier private val localSource: FilmDataSource,
    @FilmRemoteDataSourceQualifier private val remoteSource: FilmDataSource
) : FilmRepository {

    override suspend fun fetchFilmListByCharacterUrl(url: String): Either<Failure, List<String>> {
        return localSource.fetchFilmListByCharacterUrl(url)
    }

    override suspend fun fetchFilmDetailByFilmUrl(url: String): Either<Failure, Film> {
        //check if the detail is available in process light cache
        val cacheVersionResp = localSource.fetchFilmDetailByFilmUrl(url)
        if (cacheVersionResp.isRight)
            return cacheVersionResp

        //if not, make a network call
        val response = remoteSource.fetchFilmDetailByFilmUrl(url)
        if (response.isRight)
            localSource.save(url, (response as Either.Right).b)

        return response
    }

}